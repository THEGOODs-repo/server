package com.umc.TheGoods.converter.Order;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.domain.types.PayType;
import com.umc.TheGoods.web.dto.Order.OrderRequestDTO;
import com.umc.TheGoods.web.dto.Order.OrderResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderConverter {

    public static OrderResponseDTO.OrderAddResultDto toOrderAddResultDto(Orders order) {
        return OrderResponseDTO.OrderAddResultDto.builder()
                .orderId(order.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Orders toOrders(OrderRequestDTO.OrderAddDto request) {
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

    public static OrderDetail toOrderDetail(OrderRequestDTO.OrderItemDto orderItemDto, Long price) {
        return OrderDetail.builder()
                .amount(orderItemDto.getAmount())
                .orderPrice(price)
                .status(OrderStatus.PAY_PREV)
                .build();
    }

}
