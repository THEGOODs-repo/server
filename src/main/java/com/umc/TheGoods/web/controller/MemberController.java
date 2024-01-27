package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.Member.MemberConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.Member.MemberCommandServiceImpl;
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

    private final MemberCommandServiceImpl memberCommandServiceImpl;


    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "request 파라미터 : 닉네임, 비밀번호, 이메일, 생일(yyyymmdd), 성별(MALE, FEMALE, NO_SELECET), 폰번호(010xxxxxxxx),이용약관(Boolean 배열), 카테고리(Long 배열)")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request) {
        Member member = memberCommandServiceImpl.join(request);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }


    //login
    //username
    //password
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "request 파라미터 : 이메일, 비밀번호")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(@RequestBody MemberRequestDTO.LoginDTO request) {


        return ApiResponse.onSuccess(MemberConverter.toLoginResultDTO(memberCommandServiceImpl.login(request)));
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

}
