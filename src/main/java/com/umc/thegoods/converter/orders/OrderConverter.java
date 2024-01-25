package com.umc.thegoods.converter.orders;

import com.umc.thegoods.domain.order.OrderDetail;
import com.umc.thegoods.domain.order.Orders;
import com.umc.thegoods.domain.types.PayType;
import com.umc.thegoods.web.dto.order.OrderRequest;
import com.umc.thegoods.web.dto.order.OrderResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderConverter {

    public static OrderResponse.OrderAddResultDto toOrderAddResultDto(Orders order) {
        return OrderResponse.OrderAddResultDto.builder()
                .orderId(order.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Orders toOrders(OrderRequest.OrderAddDto request) {
        PayType payType = null;
        switch (request.getPayType()) {
            case "CARD":
                payType = PayType.CARD;
                break;
            case "ACCOUNT":
                payType = PayType.ACCOUNT;
                break;
        }

        return Orders.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .payType(payType)
                .refundBank(request.getRefundBank())
                .refundAccount(request.getRefundAccount())
                .refundOwner(request.getRefundOwner())
                .orderDetailList(new ArrayList<>())
                .build();
    }

    public static OrderDetail toOrderDetail(OrderRequest.OrderItemDto orderItemDto, Long price) {
        return OrderDetail.builder()
                .amount(orderItemDto.getAmount())
                .orderPrice(price)
                .build();
    }

}
