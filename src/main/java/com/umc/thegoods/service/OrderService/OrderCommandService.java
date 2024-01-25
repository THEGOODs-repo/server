package com.umc.thegoods.service.OrderService;

import com.umc.thegoods.domain.member.Member;
import com.umc.thegoods.domain.order.Orders;
import com.umc.thegoods.web.dto.order.OrderRequest;

public interface OrderCommandService {

    public Orders create(OrderRequest.OrderAddDto request, Member member);
}
