package com.umc.TheGoods.repository.order;

import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.domain.order.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findAllByStatusAndOrdersIn(OrderStatus orderStatus, List<Orders> ordersList, PageRequest pageRequest);

    Page<OrderItem> findAllByOrdersIn(List<Orders> ordersList, PageRequest pageRequest);
}
