package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.web.dto.Order.OrderRequestDTO;

public interface OrderCommandService {

    public Orders create(OrderRequestDTO.OrderAddDto request, Member member);
}
