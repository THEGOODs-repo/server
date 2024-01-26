package com.umc.TheGoods.repository.order;

import com.umc.TheGoods.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
