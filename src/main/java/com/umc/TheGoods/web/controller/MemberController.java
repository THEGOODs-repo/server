package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.MemberConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.Member.MemberCommandService;
import com.umc.TheGoods.web.dto.Member.MemberJoinRequest;
import com.umc.TheGoods.web.dto.Member.MemberLoginRequest;
import com.umc.TheGoods.web.dto.Member.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;


    @PostMapping("/join")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody MemberJoinRequest dto) {
        Member member = memberCommandService.join(dto.getNickname(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getBirthday(),
                dto.getGender(),
                dto.getPhone());
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }


    //login
    //username
    //password
    @PostMapping("/login")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(@RequestBody MemberLoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();


        return ApiResponse.onSuccess(MemberConverter.toLoginResultDTO(memberCommandService.login(email, password)));
    }


    @PostMapping("/jwt/test")
    public ResponseEntity<?> jwtTest() {


        //log.info(authentication.getAuthorities().toString());
        return ResponseEntity.ok().body("jwt 테스트 성공");
    }

}
