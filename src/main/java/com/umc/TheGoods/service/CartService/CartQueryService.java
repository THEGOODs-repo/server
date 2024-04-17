package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;

import java.util.List;

public interface CartQueryService {

    boolean isExistCart(Long cartId);

    List<Cart> getCartsByItem(Long itemId, Member member);
}
