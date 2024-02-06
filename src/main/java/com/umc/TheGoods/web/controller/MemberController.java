package com.umc.TheGoods.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.member.MemberConverter;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.MemberService.MemberCommandService;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Member", description = "Member 관련 API")
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;


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

        return ApiResponse.onSuccess(MemberConverter.toPhoneAuthConfirmFindEmailDTO(email));
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

        return ApiResponse.onSuccess(MemberConverter.toEmailAuthConfirmResultDTO(checkEmail));
    }


    @GetMapping("/kakao/callback")
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
    public ApiResponse<?> naverCallback(@RequestParam String code, String state) {

        String result = memberCommandService.naverAuth(code, state);

        if (result.startsWith("010")) {
            String phone = result.substring(0, 11);
            String email = result.substring(11);
            return ApiResponse.onFailure("로그인 실패", "회원가입 필요", MemberConverter.toSocialJoinResultDTO(phone, email));
        }

        return ApiResponse.onSuccess(MemberConverter.toSocialLoginResultDTO(result));
    }


}


