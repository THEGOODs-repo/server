package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.web.dto.order.OrderRequest;

import java.util.List;

public interface OrderQueryService {
    Boolean isOrderAvailable(List<OrderRequest.OrderItemDto> orderItemDtoList);
}
