package com.umc.TheGoods.repository.order;

import com.umc.TheGoods.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
