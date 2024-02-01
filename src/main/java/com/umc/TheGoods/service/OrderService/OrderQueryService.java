package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.web.dto.order.OrderRequestDTO;
import org.springframework.data.domain.Page;

public interface OrderQueryService {
    Page<OrderItem> getOrderItemList(Member member, OrderStatus orderStatus, Integer page);

    Page<OrderItem> getNoLoginOrderItemList(OrderRequestDTO.noLoginOrderViewDTO request);

    /**
     * orderItemId에 해당하는 orderItem을 조회. member를 통해 해당 orderItem을 조회할 권한이 있는지 검증
     *
     * @param member
     * @param orderItemId
     * @return
     */
    OrderItem getOrderItem(Member member, Long orderItemId);

    boolean isExistOrders(Long id);

    boolean isExistOrderItem(Long id);
}
