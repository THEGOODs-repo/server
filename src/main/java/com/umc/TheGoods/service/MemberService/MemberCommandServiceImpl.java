package com.umc.TheGoods.service.MemberService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.config.MailConfig;
import com.umc.TheGoods.converter.member.MemberConverter;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.repository.member.AuthRepository;
import com.umc.TheGoods.repository.member.CategoryRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import com.umc.TheGoods.repository.member.TermRepository;
import com.umc.TheGoods.web.dto.member.KakaoProfile;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.NaverProfile;
import com.umc.TheGoods.web.dto.member.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.umc.TheGoods.config.springSecurity.utils.JwtUtil.createJwt;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final BCryptPasswordEncoder encoder; // 비밀번호 암호화
    private final TermRepository termRepository;
    private final AuthRepository authRepository;
    private final MailConfig mailConfig;

    @Value("${jwt.token.secret}")
    private String key; // 토큰 만들어내는 key값
    private int expiredMs = 1000 * 60 * 60 * 24 * 5;// 토큰 만료 시간 1일

    /**
     * 회원가입 api
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public Member join(MemberRequestDTO.JoinDTO request) {

        // userNAme 중복 체크
        memberRepository.findByNickname(request.getNickname())
                .ifPresent(user -> {
                    throw new MemberHandler(ErrorStatus.MEMBER_NICKNAME_DUPLICATED);
                });

        //저장
        Member member = MemberConverter.toMember(request, encoder);

        // 카테고리 저장 로직
        List<Category> categoryList = request.getMemberCategory().stream()
                .map(category -> {
                    return categoryRepository.findById(category).orElseThrow(() -> new MemberHandler(ErrorStatus.CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());

        List<MemberCategory> memberCategoryList = MemberConverter.toMemberCategoryList(categoryList);

        memberCategoryList.forEach(memberCategory -> {
            memberCategory.setMember(member);
        });

        // 약관동의 저장 로직
        HashMap<Term, Boolean> termMap = new HashMap<>();
        for (int i = 0; i < request.getMemberTerm().size(); i++) {
            termMap.put(termRepository.findById(i + 1L).orElseThrow(() -> new MemberHandler(ErrorStatus.TERM_NOT_FOUND)), request.getMemberTerm().get(i));

        }

        List<MemberTerm> memberTermList = MemberConverter.toMemberTermList(termMap);
        memberTermList.forEach(memberTerm -> {
            memberTerm.setMember(member);
        });
        memberRepository.save(member);
        return member;
    }

    /**
     * 로그인 api
     *
     * @return
     */
    @Override
    public String login(MemberRequestDTO.LoginDTO request) {

        //email 없음
        Member selectedMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_EMAIL_NOT_FOUND));


        //password 틀림
        if (!encoder.matches(request.getPassword(), selectedMember.getPassword())) {
            throw new MemberHandler(ErrorStatus.MEMBER_PASSWORD_ERROR);
        }

        //사용자 권한
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        return createJwt(selectedMember.getId(), selectedMember.getNickname(), expiredMs, key, roles);

    }

    /**
     * 폰 인증번호 전송 api
     */
    @Override
    @Transactional
    public Auth sendPhoneAuth(String phone) {

        String code = Integer.toString((int) (Math.random() * 8999) + 1000);

        Boolean expired = false;

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        //HttpBody 객체 생성
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("target", phone);
        params.put("password", "junjunjun");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Json 에러", e);
        }
        HttpEntity<String> phoneAuthRequest = new HttpEntity<>(jsonBody, headers);


        //Http 요청해서 응답 받음
        ResponseEntity<Void> response = rt.exchange(
                "http://116.39.207.35:8000/smsroute",
                HttpMethod.POST,
                phoneAuthRequest,
                Void.class
        );

        Auth auth = MemberConverter.toPhoneAuth(phone, code, expired);
        Optional<Auth> delete = authRepository.findByPhone(phone);

        if (delete.isPresent()) {

            Auth deleteAuth = delete.orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR));
            authRepository.delete(deleteAuth);
        }

        authRepository.save(auth);
        return auth;
    }

    @Override
    @Transactional
    public Boolean confirmPhoneAuth(MemberRequestDTO.PhoneAuthConfirmDTO request) {
        Boolean checkPhone = false;
        Auth auth = authRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR));
        LocalDateTime current = LocalDateTime.now();
        //인증 코드 같은지 확인, 이미 사용된 인증인지 체크, 만료시간 지났는지 체크
        if (auth.getCode().equals(request.getCode()) && !auth.getExpired() && auth.getExpireDate().isAfter(current)) {
            checkPhone = true;

            //phoneAuth expired true로 update 코드
            auth.useToken();
            authRepository.save(auth);

        } else {
            throw new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR);
        }

        return checkPhone;
    }

    @Override
    public Boolean confirmEmailDuplicate(MemberRequestDTO.EmailDuplicateConfirmDTO request) {

        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Boolean checkEmail = false;

        if (!member.isPresent()) {
            checkEmail = true;
        }
        return checkEmail;
    }

    @Override
    public Boolean confirmNicknameDuplicate(MemberRequestDTO.NicknameDuplicateConfirmDTO request) {
        Optional<Member> member = memberRepository.findByNickname(request.getNickname());

        Boolean checkNickname = false;

        if (!member.isPresent()) {
            checkNickname = true;
        }
        return checkNickname;
    }

    @Override
    @Transactional
    public String confirmPhoneAuthFindEmail(MemberRequestDTO.PhoneAuthConfirmFindEmailDTO request) {

        Auth auth = authRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR));
        String email = "";
        LocalDateTime current = LocalDateTime.now();
        //코드, 만료 됐는지, 만료 시간 체크
        if (auth.getCode().equals(request.getCode()) && !auth.getExpired() && auth.getExpireDate().isAfter(current)) {
            Optional<Member> member = memberRepository.findByPhone(request.getPhone());
            log.info(member.get().getEmail());
            if (member.isPresent()) {
                email = member.get().getEmail();
                //사용한 인증 만료로 변
                auth.useToken();
                authRepository.save(auth);

            } else {
                throw new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR);
            }
        } else {
            throw new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR);
        }
        return email;

    }

    @Override
    @Transactional
    public Auth sendEmailAuth(String email) {

        String code = Integer.toString((int) (Math.random() * 8999) + 1000);
        Boolean expired = false;

        mailConfig.sendMail(email, code);

        Auth auth = MemberConverter.toEmailAuth(email, code, expired);
        Optional<Auth> delete = authRepository.findByEmail(email);

        if (delete.isPresent()) {

            Auth deleteAuth = delete.orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_EMAIL_AUTH_ERROR));
            authRepository.delete(deleteAuth);
        }

        authRepository.save(auth);
        return auth;

    }

    @Override
    @Transactional
    public Boolean confirmEmailAuth(MemberRequestDTO.EmailAuthConfirmDTO request) {
        Boolean checkEmail = false;
        Auth auth = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_EMAIL_AUTH_ERROR));
        LocalDateTime current = LocalDateTime.now();
        //인증 코드 같은지 확인, 이미 사용된 인증인지 체크, 만료시간 지났는지 체크
        if (auth.getCode().equals(request.getCode()) && !auth.getExpired() && auth.getExpireDate().isAfter(current)) {
            checkEmail = true;

            //phoneAuth expired true로 update 코드
            auth.useToken();
            authRepository.save(auth);

        } else {
            throw new MemberHandler(ErrorStatus.MEMBER_EMAIL_AUTH_ERROR);
        }

        return checkEmail;
    }

    @Override
    @Transactional
    public String kakaoAuth(String code) {

        String jwt;

        //Post방식으로 key=value 데이터를 요청
        RestTemplate rt = new RestTemplate();

        //HttpHeader 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "49ff7dc7f5309c49f75ac2a087ffe91e");
        params.add("redirect_uri", "http://localhost:8080/api/members/kakao/callback");
        params.add("code", code);
        //params.add("client_secret","");

        //HttpHeader와 HttpBody를 하나의 객체로 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청해서 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("카카오 토큰:" + oAuthToken.getAccess_token());


        ///////////////////////////
        //토큰 이용해서 정보 가져오기

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader와 HttpBody를 하나의 객체로 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String phone = kakaoProfile.kakao_account.phone_number.replaceAll("[^0-9]", "");
        if (phone.startsWith("82")) {
            phone = "0" + phone.substring(2);
        }


        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member.isPresent()) {
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            return createJwt(member.get().getId(), member.get().getNickname(), expiredMs, key, roles);

        }

        return phone + kakaoProfile.getKakao_account().email;
    }

    @Override
    @Transactional
    public String naverAuth(String code, String state) {
        RestTemplate rt = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "t6q4Bn70dY7Kli7hS58P");
        params.add("redirect_uri", "http://localhost:8080/api/members/naver/callback");
        params.add("client_secret", "1uPpEHHTBF");
        params.add("code", code);
        params.add("state", state);


        //HttpHeader와 HttpBody를 하나의 객체로 담기
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청해서 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("네이버 토큰:" + oAuthToken.getAccess_token());


        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader와 HttpBody를 하나의 객체로 담기
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );

        log.info(response2.toString());
        ObjectMapper objectMapper2 = new ObjectMapper();
        NaverProfile naverProfile = null;

        try {
            naverProfile = objectMapper2.readValue(response2.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String phone = naverProfile.response.mobile.replaceAll("[^0-9]", "");

        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member.isPresent()) {
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            return createJwt(member.get().getId(), member.get().getNickname(), expiredMs, key, roles);

        }

        return phone + naverProfile.getResponse().email;
    }

    /**
     * 카테고리 validator
     */
    @Override
    public boolean existCategoryById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}

