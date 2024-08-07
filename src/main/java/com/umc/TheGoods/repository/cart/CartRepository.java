package com.umc.TheGoods.repository.cart;

import com.umc.TheGoods.domain.enums.CartStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMemberAndItemIdAndCartStatus(Member member, Long itemId, CartStatus cartStatus);

    List<Cart> findAllByMemberAndCartStatusOrderByItemIdAsc(Member member, CartStatus cartStatus);

    Optional<Cart> findByMemberAndItemAndItemOptionAndCartStatus(Member member, Item item, ItemOption itemOption, CartStatus cartStatus);

    Optional<Cart> findByMemberAndItemAndCartStatus(Member member, Item item, CartStatus cartStatus);

}
