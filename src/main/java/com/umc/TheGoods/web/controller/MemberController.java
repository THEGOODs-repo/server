package com.umc.TheGoods.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.Member.MemberConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.PhoneAuth;
import com.umc.TheGoods.service.Member.MemberCommandService;
import com.umc.TheGoods.web.dto.Member.MemberDetail;
import com.umc.TheGoods.web.dto.Member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.Member.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Member", description = "Member 관련 API")
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;

    public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    public static final String AUTH_TOKEN = "your_auth_token";


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


        return ApiResponse.onSuccess(MemberConverter.toLoginResultDTO(memberCommandService.login(request)));
    }


    @PostMapping("/jwt/test")
    @Operation(summary = "jwt test API", description = "테스트 용도 api")
    public ResponseEntity<?> jwtTest(Authentication authentication) {
        //request값으로 Bearer {jwt} 값을 넘겨주면 jwt를 해석해서 Authentication에 정보가 담기고 담긴 정보를 가공해서 사용
        //jwt 토큰은 회원가입하고 로그인하면 발급받을 수 있습니다.
        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();

        return ResponseEntity.ok().body("memberId: " + memberDetail.getMemberId() +
                " memberName: " + memberDetail.getMemberName() +
                " memberRole: " + memberDetail.getMemberRole());
    }


    @PostMapping("/phone/auth")
    @Operation(summary = "휴대폰 인증 번호 요청 API", description = "request: 휴대폰 번호, response : 인증번호")
    public ApiResponse<MemberResponseDTO.PhoneAuthSendResultDTO> phoneAuthSend(@RequestBody MemberRequestDTO.PhoneAuthDTO request) throws JsonProcessingException {

        PhoneAuth phoneAuth = memberCommandService.sendPhoneAuth(request.getPhone());

        return ApiResponse.onSuccess(MemberConverter.toPhoneAuthSendResultDTO(phoneAuth));
    }


    @PostMapping("phone/auth/verify")
    @Operation(summary = "휴대폰 인증 번호 확인 API", description = "request: 인증 코드, response : 인증완료 true")
    public ApiResponse<MemberResponseDTO.PhoneAuthConfirmResultDTO> phoneAuth(@RequestBody MemberRequestDTO.PhoneAuthConfirmDTO request) {
        Boolean checkPhone = memberCommandService.confirmPhoneAuth(request);

        return ApiResponse.onSuccess(MemberConverter.toPhoneAuthConfirmResultDTO(checkPhone));
    }

    @PostMapping("email/duplicate")
    @Operation(summary = "이메일 중복 체크 API", description = "request : 이메일, response : 중복이면 true, 중복이 아니면 false")
    public ApiResponse<MemberResponseDTO.EmailDuplicateConfirmResultDTO> emailDuplicate(@RequestBody MemberRequestDTO.EmailDuplicateConfirmDTO request) {
        Boolean checkEmail = memberCommandService.confirmEmailDuplicate(request);

        return ApiResponse.onSuccess(MemberConverter.toEmailDuplicateConfirmResultDTO(checkEmail));
    }
}
