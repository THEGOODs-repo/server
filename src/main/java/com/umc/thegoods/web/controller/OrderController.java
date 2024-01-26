package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.orders.OrderConverter;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.service.MemberTempService;
import com.umc.TheGoods.service.OrderService.OrderCommandService;
import com.umc.TheGoods.web.dto.order.OrderRequest;
import com.umc.TheGoods.web.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final MemberTempService memberTempService;

    @PostMapping("/")
    public ApiResponse<OrderResponse.OrderAddResultDto> order(@RequestBody OrderRequest.OrderAddDto request) {
        log.info("request 정보: ", request.toString());

        Member tempMember = memberTempService.findMember(1L);

        Orders orders = orderCommandService.create(request, tempMember);
        return ApiResponse.onSuccess(OrderConverter.toOrderAddResultDto(orders));
    }

}
