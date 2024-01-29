package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.order.OrderDetail;
import org.springframework.data.domain.Page;

public interface OrderQueryService {
    Page<OrderDetail> getOrderDetailList(Long memberId, Integer page);
}
