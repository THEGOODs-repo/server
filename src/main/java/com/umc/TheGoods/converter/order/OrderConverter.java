package com.umc.TheGoods.converter.order;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.domain.types.PayType;
import com.umc.TheGoods.web.dto.order.OrderRequestDTO;
import com.umc.TheGoods.web.dto.order.OrderResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static OrderResponseDTO.OrderPreViewDTO toOrderPreViewDTO(OrderDetail orderDetail) {
        String itemOptionName;
        if (orderDetail.getItemOption() != null) {
            itemOptionName = orderDetail.getItemOption().getName();
        } else {
            itemOptionName = null;
        }

        return OrderResponseDTO.OrderPreViewDTO.builder()
                .orderDetailId((orderDetail.getId()))
                .orderStatus(orderDetail.getStatus())
                .orderDateTime(orderDetail.getCreatedAt())
                .itemName(orderDetail.getItem().getName())
                .optionName(itemOptionName)
                .imgUrl("img url") // 추후 썸네일 이미지 가져오는 메소드 통해 값 입력 필요
                .amount(orderDetail.getAmount())
                .price(orderDetail.getOrderPrice())
                .build();
    }

    public static OrderResponseDTO.OrderPreViewListDTO toOrderPreViewListDTO(Page<OrderDetail> orderDetailList) {
        List<OrderResponseDTO.OrderPreViewDTO> orderPreViewDTOList = orderDetailList.stream()
                .map(OrderConverter::toOrderPreViewDTO).collect(Collectors.toList());

        return OrderResponseDTO.OrderPreViewListDTO.builder()
                .isLast(orderDetailList.isLast())
                .isFirst(orderDetailList.isFirst())
                .totalPage(orderDetailList.getTotalPages())
                .totalElements(orderDetailList.getTotalElements())
                .listSize(orderPreViewDTOList.size())
                .orderPreViewDTOList(orderPreViewDTOList)
                .build();
    }
}
