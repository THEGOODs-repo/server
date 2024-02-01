package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.web.dto.order.OrderRequestDTO;

public interface OrderCommandService {

    Orders create(OrderRequestDTO.OrderAddDTO request, Member member);

    OrderItem updateOrderItemAddress(OrderRequestDTO.OrderItemAddressUpdateDTO request, Long orderItemId, Member member);

    OrderItem updateOrderItemRefundInfo(OrderRequestDTO.OrderItemRefundInfoUpdateDTO request, Long orderItemId, Member member);
}
