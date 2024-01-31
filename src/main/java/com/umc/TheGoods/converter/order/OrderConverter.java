package com.umc.TheGoods.converter.order;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.OrderItem;
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

    public static OrderResponseDTO.OrderAddResultDTO toOrderAddResultDto(Orders order) {
        return OrderResponseDTO.OrderAddResultDTO.builder()
                .orderId(order.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Orders toOrders(OrderRequestDTO.OrderAddDTO request) {
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
                .payType(payType)
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static OrderItem toOrderItem(OrderRequestDTO.OrderAddDTO request, Integer deliveryFee) {
        return OrderItem.builder()
                .totalPrice(0L)
                .deliveryFee(deliveryFee)
                .status(OrderStatus.PAY_PREV)
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .deliveryMemo(request.getDeliveryMemo())
                .refundBank(request.getRefundBank())
                .refundAccount(request.getRefundAccount())
                .refundOwner(request.getRefundOwner())
                .depositor(request.getDepositor())
                .orderDetailList(new ArrayList<>())
                .build();
    }

    public static OrderDetail toOrderDetail(OrderRequestDTO.OrderDetailDTO orderDetailDTO, Long price) {
        return OrderDetail.builder()
                .amount(orderDetailDTO.getAmount())
                .orderPrice(orderDetailDTO.getAmount() * price)
                .build();
    }

    public static OrderResponseDTO.OrderItemPreViewDTO toOrderItemPreViewDTO(OrderItem orderItem) {

        Boolean hasOption = orderItem.getItem().getPrice() == null;

        Integer orderDetailCount = orderItem.getOrderDetailList().size();

        String itemOptionName;
        if (hasOption) { // 해당 주문 상품에 옵션이 있는 경우
            itemOptionName = orderItem.getOrderDetailList().get(0).getItemOption().getName(); // 대표 옵션 명 설정
            itemOptionName += orderDetailCount > 1 ? " 외 " + orderDetailCount.toString() + "건" : " 1건"; // optionString 설정
        } else {
            itemOptionName = null;
        }

        return OrderResponseDTO.OrderItemPreViewDTO.builder()
                .orderItemId((orderItem.getId()))
                .orderStatus(orderItem.getStatus())
                .orderDateTime(orderItem.getCreatedAt())
                .itemName(orderItem.getItem().getName())
                .optionString(itemOptionName)
                .imgUrl("img url") // 추후 썸네일 이미지 가져오는 메소드 통해 값 입력 필요
                .price(orderItem.getTotalPrice())
                .build();
    }

    public static OrderResponseDTO.orderItemPreViewListDTO toOrderPreViewListDTO(Page<OrderItem> orderItemList) {
        List<OrderResponseDTO.OrderItemPreViewDTO> orderItemPreViewDTOList = orderItemList.stream()
                .map(OrderConverter::toOrderItemPreViewDTO).collect(Collectors.toList());

        return OrderResponseDTO.orderItemPreViewListDTO.builder()
                .isLast(orderItemList.isLast())
                .isFirst(orderItemList.isFirst())
                .totalPage(orderItemList.getTotalPages())
                .totalElements(orderItemList.getTotalElements())
                .listSize(orderItemPreViewDTOList.size())
                .orderItemPreViewDTOList(orderItemPreViewDTOList)
                .build();
    }
}
