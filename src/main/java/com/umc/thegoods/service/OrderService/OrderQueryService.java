package com.umc.thegoods.service.OrderService;

import com.umc.thegoods.web.dto.order.OrderRequest;

import java.util.List;

public interface OrderQueryService {
    public Boolean isOrderAvailable(List<OrderRequest.OrderItemDto> orderItemDtoList);
}
