package com.umc.TheGoods.service.Member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.Member.MemberConverter;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.PhoneAuth;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.repository.CategoryRepository;
import com.umc.TheGoods.repository.MemberRepository;
import com.umc.TheGoods.repository.PhoneAuthRepository;
import com.umc.TheGoods.repository.TermRepository;
import com.umc.TheGoods.web.dto.Member.MemberRequestDTO;
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
import org.springframework.web.client.RestTemplate;

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
    private final PhoneAuthRepository phoneAuthRepository;

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
    public PhoneAuth sendPhoneAuth(String phone) {

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

        PhoneAuth phoneAuth = MemberConverter.toPhoneAuth(phone, code, expired);
        phoneAuthRepository.save(phoneAuth);
        return phoneAuth;
    }

    @Override
    public Boolean confirmPhoneAuth(MemberRequestDTO.PhoneAuthConfirmDTO request) {
        Boolean checkPhone = false;
        PhoneAuth phoneAuth = phoneAuthRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_PHONE_AUTH_ERROR));

        if (phoneAuth.getCode().equals(request.getCode())) {
            checkPhone = true;
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

    /**
     * 카테고리 validator
     */
    @Override
    public boolean existCategoryById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}

