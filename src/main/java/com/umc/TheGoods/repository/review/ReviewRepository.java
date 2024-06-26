package com.umc.TheGoods.repository.review;

import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByOrderItem(OrderItem orderItem);
}
