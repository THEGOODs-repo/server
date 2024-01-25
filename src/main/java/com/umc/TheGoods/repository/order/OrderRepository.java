package com.umc.thegoods.repository.order;

import com.umc.thegoods.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
