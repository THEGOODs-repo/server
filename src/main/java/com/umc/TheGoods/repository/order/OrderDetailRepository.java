package com.umc.thegoods.repository.order;

import com.umc.thegoods.domain.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
