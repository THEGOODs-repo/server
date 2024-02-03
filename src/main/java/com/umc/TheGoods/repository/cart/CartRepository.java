package com.umc.TheGoods.repository.cart;

import com.umc.TheGoods.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
