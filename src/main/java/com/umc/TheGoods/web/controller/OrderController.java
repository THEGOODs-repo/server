package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.order.OrderConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.OrderService.OrderCommandService;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import com.umc.TheGoods.web.dto.order.OrderRequestDTO;
import com.umc.TheGoods.web.dto.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Order", description = "주문 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping
    @Operation(summary = "주문 등록 API", description = "상품에 대한 주문을 등록하는 API 입니다.\n\n" +
            "payType(결제 유형)은 \"ACCOUNT\" 또는 \"CARD\"로 입력해야 합니다.\n\n" +
            "orderDetailDTOList 내부의 JSON에는 itemOptionId(상품 옵션 id)와 amount(주문 수량)을 담아주세요. 해당 상품이 단일 상품이라면 itemOptionId 값은 보내지 않아도 됩니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<OrderResponseDTO.OrderAddResultDto> order(@RequestBody @Valid OrderRequestDTO.OrderAddDto request, Authentication authentication) {
        Member member = null;

        // 비로그인 요청인 경우 비회원 계정 불러오기
        if (authentication == null) {
            member = memberQueryService.findMemberByNickname("no_login_user").orElseThrow(() -> new OrderHandler(ErrorStatus.NO_LOGIN_ORDER_NOT_AVAILABLE));
        } else {
            // request에서 member id 추출해 Member 엔티티 찾기
            MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
            member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        }

        Orders orders = orderCommandService.create(request, member);

        return ApiResponse.onSuccess(OrderConverter.toOrderAddResultDto(orders));
    }

}
