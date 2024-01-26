package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.orders.OrderConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.service.MemberTempService;
import com.umc.TheGoods.service.OrderService.OrderCommandService;
import com.umc.TheGoods.web.dto.order.OrderRequest;
import com.umc.TheGoods.web.dto.order.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Order", description = "주문 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final MemberTempService memberTempService;

    @PostMapping
    @Operation(summary = "주문 등록 API", description = "상품에 대한 주문을 등록하는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<OrderResponse.OrderAddResultDto> order(@RequestBody @Valid OrderRequest.OrderAddDto request) {

        Member tempMember = memberTempService.findMember(1L);
        Orders orders = orderCommandService.create(request, tempMember);

        return ApiResponse.onSuccess(OrderConverter.toOrderAddResultDto(orders));
    }

}
