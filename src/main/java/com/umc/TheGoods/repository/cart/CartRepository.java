package com.umc.TheGoods.repository.cart;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberAndItem(Member member, Item item);

    List<Cart> findAllByMember(Member member);

    @Modifying
    @Query("delete from Cart c where c.id=:id")
    void deleteCartById(@Param("id") Long cartId);
}
