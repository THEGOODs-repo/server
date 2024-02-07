package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.CartService.CartCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "장바구니 담기 API", description = "해당 상품/옵션을 장바구니에 추가하는 API 입니다.\n\n" +
            "amount는 해당 상품이 단일 상품인 경우에만 값 입력이 필요합니다.\n\n" +
            "cartOptionAddDTOList 내부 JSON의 amount는 해당 상품이 옵션이 있는 경우 반드시 값 입력이 필요합니다.\n\n" +
            "상품이 단일 상품인 경우, cartOptionAddDTOList는 빈 array를 넘겨주세요.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
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

    @PutMapping
    @Operation(summary = "장바구니 옵션 수정 API", description = "해당 장바구니 내역의 담은 수량을 수정하는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<String> update(@RequestBody @Valid CartRequestDTO.cartUpdateDTO request,
                                      Authentication authentication) {
        // 비회원인 경우 처리 불가
        if (authentication == null) {
            throw new MemberHandler(ErrorStatus._UNAUTHORIZED);
        }

        // request에서 member id 추출해 Member 엔티티 찾기
        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        cartCommandService.updateCart(request, member);

        return ApiResponse.onSuccess("장바구니 옵션 수정 성공");
    }


}
