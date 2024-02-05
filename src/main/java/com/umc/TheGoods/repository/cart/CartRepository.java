package com.umc.TheGoods.repository.cart;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberAndItemAndItemOption(Member member, Item item, ItemOption itemOption);

    Optional<Cart> findByMemberAndItem(Member member, Item item);

}
