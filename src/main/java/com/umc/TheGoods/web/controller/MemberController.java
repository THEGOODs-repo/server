package com.umc.TheGoods.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.code.status.SuccessStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.item.ItemConverter;
import com.umc.TheGoods.converter.member.MemberConverter;
import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.enums.MemberStatus;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.redis.domain.RefreshToken;
import com.umc.TheGoods.redis.service.RedisService;
import com.umc.TheGoods.service.ItemService.ItemQueryService;
import com.umc.TheGoods.service.MemberService.MemberCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.OrderService.OrderQueryService;
import com.umc.TheGoods.validation.annotation.CheckPage;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Member", description = "Member 관련 API")
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final RedisService redisService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "request 파라미터 : 닉네임, 비밀번호(String), 이메일, 생일(yyyymmdd), 성별(MALE, FEMALE, NO_SELECET), 폰번호(010xxxxxxxx),이용약관(Boolean 배열), 카테고리(Long 배열)")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request) {
        Member member = memberCommandService.join(request);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }


    //login
    //username
    //password
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "request 파라미터 : 이메일, 비밀번호(String)")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(@RequestBody MemberRequestDTO.LoginDTO request) {


        return ApiResponse.onSuccess(memberCommandService.login(request));
    }

    @Operation(summary = "로그아웃 API", description = "로그아웃 API 입니다.")
    @PostMapping("/logout")
    public ApiResponse<MemberResponseDTO.LogoutResultDTO> logout(Authentication authentication,
                                                                 HttpServletRequest authorizationHeader) {
        String token = authorizationHeader.getHeader("Authorization").substring(7);
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        memberCommandService.logout(token, member);
        return ApiResponse.onSuccess(MemberConverter.toLogoutResultDTO(member));
    }

    @Operation(summary = "리프레쉬 토큰을 이용해 accessToken 재발급 API ️", description = "리프레쉬 토큰으로 accessToken 재발급하는 API입니다.")
    @PostMapping("/token/regenerate")
    public ApiResponse<MemberResponseDTO.NewTokenDTO> getNewToken(@RequestBody MemberRequestDTO.RefreshTokenDTO request) {
        RefreshToken newRefreshToken = redisService.reGenerateRefreshToken(request);
        String accessToken = memberCommandService.regenerateAccessToken(newRefreshToken);
        return ApiResponse.onSuccess(MemberConverter.toNewTokenDTO(accessToken, newRefreshToken.getToken()));
    }

    @PostMapping("/jwt/test")
    @Operation(summary = "jwt test API", description = "테스트 용도 api")
    public ResponseEntity<?> jwtTest(Authentication authentication) {
        //request값으로 Bearer {jwt} 값을 넘겨주면 jwt를 해석해서 Authentication에 정보가 담기고 담긴 정보를 가공해서 사용
        //jwt 토큰은 회원가입하고 로그인하면 발급받을 수 있습니다.
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return ResponseEntity.ok().body(member.getEmail());
    }


    @PostMapping("/phone/auth")
    @Operation(summary = "휴대폰 인증 번호 요청 API", description = "request: 휴대폰 번호, response : 인증번호")
    public ApiResponse<MemberResponseDTO.PhoneAuthSendResultDTO> phoneAuthSend(@RequestBody MemberRequestDTO.PhoneAuthDTO request) throws JsonProcessingException {

        Auth auth = memberCommandService.sendPhoneAuth(request.getPhone());

        return ApiResponse.onSuccess(MemberConverter.toPhoneAuthSendResultDTO(auth));
    }


    @PostMapping("phone/auth/verify")
    @Operation(summary = "회원가입시 휴대폰 인증 번호 확인 API", description = "request: 인증 코드, response : 인증완료 true")
    public ApiResponse<MemberResponseDTO.PhoneAuthConfirmResultDTO> phoneAuth(@RequestBody MemberRequestDTO.PhoneAuthConfirmDTO request) {
        Boolean checkPhone = memberCommandService.confirmPhoneAuth(request);

        return ApiResponse.onSuccess(MemberConverter.toPhoneAuthConfirmResultDTO(checkPhone));
    }

    @PostMapping("email/duplicate")
    @Operation(summary = "이메일 중복 체크 API", description = "request : 이메일, response : 중복이면 false, 중복이 아니면 true")
    public ApiResponse<MemberResponseDTO.EmailDuplicateConfirmResultDTO> emailDuplicate(@RequestBody MemberRequestDTO.EmailDuplicateConfirmDTO request) {
        Boolean checkEmail = memberCommandService.confirmEmailDuplicate(request);

        return ApiResponse.onSuccess(MemberConverter.toEmailDuplicateConfirmResultDTO(checkEmail));
    }

    @PostMapping("nickname/duplicate")
    @Operation(summary = "닉네임 중복 체크 API", description = "request : 닉네임, response: 중복이면 false, 중복 아니면 true")
    public ApiResponse<MemberResponseDTO.NicknameDuplicateConfirmResultDTO> nicknameDuplicate(@RequestBody MemberRequestDTO.NicknameDuplicateConfirmDTO request) {
        Boolean checkNickname = memberCommandService.confirmNicknameDuplicate(request);

        return ApiResponse.onSuccess(MemberConverter.toNicknameDuplicateConfirmResultDTO(checkNickname));
    }

    @PostMapping("phone/auth/verify/find/email")
    @Operation(summary = "이메일 찾기에서 사용되는 번호 확인 api", description = "request: 인증 코드, response: 인증 완료 되면 email")
    public ApiResponse<MemberResponseDTO.PhoneAuthConfirmFindEmailResultDTO> phoneAuthFindEmail(@RequestBody MemberRequestDTO.PhoneAuthConfirmFindEmailDTO request) {
        String email = memberCommandService.confirmPhoneAuthFindEmail(request);

        Member member = memberQueryService.findMemberByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Optional<ProfileImg> profileImg = memberQueryService.findProfileImgByMember(member.getId());

        if(profileImg.isEmpty()){
            return ApiResponse.onSuccess(MemberConverter.toPhoneAuthConfirmFindEmailDTO(email, null));
        }
        else {
            return ApiResponse.onSuccess(MemberConverter.toPhoneAuthConfirmFindEmailDTO(email, profileImg.get().getUrl()));
        }
    }

    @PostMapping("email/auth")
    @Operation(summary = "비밀번호 찾기에서 사용되는 email 인증 요청 api", description = "request: 이메일 입력하면 해당 이메일로 인증번호 전송")
    public ApiResponse<MemberResponseDTO.EmailAuthSendResultDTO> emailAuthSend(@RequestBody MemberRequestDTO.EmailAuthDTO request) {

        Auth auth = memberCommandService.sendEmailAuth(request.getEmail());
        return ApiResponse.onSuccess(MemberConverter.toEmailAuthSendResultDTO(auth));
    }

    @PostMapping("email/auth/verify")
    @Operation(summary = "비밀번호 찾기에서 사용되는 email 인증 코드 검증 api", description = "request: 이메일, 코드 response: 인증완료 true")
    public ApiResponse<MemberResponseDTO.EmailAuthConfirmResultDTO> emailAuth(@RequestBody MemberRequestDTO.EmailAuthConfirmDTO request) {
        Boolean checkEmail = memberCommandService.confirmEmailAuth(request);


        if (checkEmail == true) {
            String jwt = memberCommandService.emailAuthCreateJWT(request);
            return ApiResponse.onSuccess(MemberConverter.toEmailAuthConfirmResultDTO(checkEmail, jwt));
        }

        return ApiResponse.onSuccess(MemberConverter.toEmailAuthConfirmResultDTO(checkEmail, null));

    }

    @PostMapping("password/update")
    @Operation(summary = "새로운 비밀번호 설정 api", description = "request: 비밀번호, 비밀번호 확인 response: 변경완료 true")
    public ApiResponse<MemberResponseDTO.PasswordUpdateResultDTO> updatePassword(@RequestBody MemberRequestDTO.PasswordUpdateDTO request, Authentication authentication) {

        if (request.getPassword() != request.getCheckPassword()) {
            new MemberHandler(ErrorStatus.MEMBER_PASSWORD_NOT_EQUAL);
        }

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Boolean updatePassword = memberCommandService.updatePassword(request, member);

        return ApiResponse.onSuccess(MemberConverter.toPasswordUpdateResultDTO(updatePassword));
    }


    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오 소셜 로그인 api", description = "callback 용도 api여서 swagger에서 test 안됩니다")
    public ApiResponse<?> kakaoCallback(@RequestParam String code) {

        String result = memberCommandService.kakaoAuth(code);

        //반환한 카카오 프로필에서 기존 회원이면 jwt 토큰 반환 아니면 회원가입 진행
        //토큰 반환은 쉽지만 회원가입 로직으로 보내야하면 false 반환해서 회원가입 진행하도록하기

        if (result.startsWith("010")) {
            String phone = result.substring(0, 11);
            String email = result.substring(11);
            return ApiResponse.onFailure("로그인 실패", "회원가입 필요", MemberConverter.toSocialJoinResultDTO(phone, email));
        }

        return ApiResponse.onSuccess(MemberConverter.toSocialLoginResultDTO(result));
    }

    @GetMapping("/naver/callback")
    @Operation(summary = "네이버 소셜 로그인 api", description = "callback 용도 api여서 swagger에서 test 안됩니다")
    public ApiResponse<?> naverCallback(@RequestParam String code, String state) {

        String result = memberCommandService.naverAuth(code, state);

        if (result.startsWith("010")) {
            String phone = result.substring(0, 11);
            String email = result.substring(11);
            return ApiResponse.onFailure("로그인 실패", "회원가입 필요", MemberConverter.toSocialJoinResultDTO(phone, email));
        }

        return ApiResponse.onSuccess(MemberConverter.toSocialLoginResultDTO(result));
    }


    @GetMapping(value = "/profile")
    @Operation(summary = "프로필 조회 api", description = "프로필이미지, 닉네임을 조회할 수 있습니다.")
    public ApiResponse<MemberResponseDTO.ProfileResultDTO> getProfile(Authentication authentication) {



        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Optional<ProfileImg> profileImg = memberQueryService.findProfileImgByMember(member.getId());
        List<Address> address = memberQueryService.findAllAddressById(member.getId());
        List<Account> account = memberQueryService.findAllAccountById(member.getId());



        if(profileImg.isEmpty()){
            return ApiResponse.onSuccess(MemberConverter.toProfile(member, null, account, address));
        }
        else {
            return ApiResponse.onSuccess(MemberConverter.toProfile(member, profileImg.get().getUrl(),account, address));
        }


    }



    @PutMapping(value = "/phone/name/update")
    @Operation(summary = "주문시 고객 정보 수정(이름, 번호) api", description = "이름과 번호 수정할 수 있는 기능")
    public ApiResponse<MemberResponseDTO.PhoneNameUpdateResultDTO> updateRole(@RequestBody MemberRequestDTO.PhoneNameUpdateDTO request, Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        memberCommandService.updatePhoneName(request, member);



        return ApiResponse.onSuccess(null);
    }







}


