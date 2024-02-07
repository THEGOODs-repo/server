package com.umc.TheGoods.repository.cart;

import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.domain.order.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartAndItemOption(Cart cart, ItemOption itemOption);

    Optional<CartDetail> findByCart(Cart cart);

}
