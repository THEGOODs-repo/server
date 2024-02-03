package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.CartService.CartCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Cart", description = "장바구니 관련 API")
//@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final MemberQueryService memberQueryService;
    private final CartCommandService cartCommandService;

    @PostMapping
    public ApiResponse<String> add(@RequestBody @Valid CartRequestDTO.cartAddDTO request,
                                   Authentication authentication) {
        // 비회원인 경우 처리 불가
        if (authentication == null) {
            throw new MemberHandler(ErrorStatus._UNAUTHORIZED);
        }

        // request에서 member id 추출해 Member 엔티티 찾기
        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        cartCommandService.addCart(request, member);

        return ApiResponse.onSuccess("장바구니 담기 성공");
    }


}
