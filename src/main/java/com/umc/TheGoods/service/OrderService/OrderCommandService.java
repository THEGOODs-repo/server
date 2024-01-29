package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.web.dto.order.OrderRequest;

public interface OrderCommandService {

    public Orders create(OrderRequest.OrderAddDto request, Long memberId);
}
