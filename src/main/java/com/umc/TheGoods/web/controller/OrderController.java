package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.converter.order.OrderConverter;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.service.OrderService.OrderCommandService;
import com.umc.TheGoods.service.OrderService.OrderQueryService;
import com.umc.TheGoods.web.dto.Member.MemberDetail;
import com.umc.TheGoods.web.dto.order.OrderRequest;
import com.umc.TheGoods.web.dto.order.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Order", description = "주문 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    @Operation(summary = "주문 등록 API", description = "상품에 대한 주문을 등록하는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<OrderResponse.OrderAddResultDto> createOrder(@RequestBody @Valid OrderRequest.OrderAddDto request, Authentication authentication) {
        // request에서 member 정보 추출
        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();

        Orders orders = orderCommandService.create(request, memberDetail.getMemberId());

        return ApiResponse.onSuccess(OrderConverter.toOrderAddResultDto(orders));
    }

    @GetMapping
    @Operation(summary = "나의 주문 목록 조회 API", description = "나의 주문 목록을 조회하는 API 입니다. (구매자)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    public ApiResponse<OrderResponse.OrderPreViewListDTO> myOrderPreview(@RequestParam(name = "page") Integer page, Authentication authentication) {
        // request에서 member id 추출해 Member 엔티티 찾기
        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Page<OrderDetail> orderDetailList = orderQueryService.getOrderDetailList(memberDetail.getMemberId(), page - 1);

        return ApiResponse.onSuccess(OrderConverter.toOrderPreViewListDTO(orderDetailList));
    }

}
