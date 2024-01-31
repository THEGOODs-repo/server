package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import org.springframework.data.domain.Page;

public interface OrderQueryService {
    Page<OrderItem> getOrderItemList(Member member, OrderStatus orderStatus, Integer page);
}
