package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;

import java.util.List;

public interface CartQueryService {

    List<Cart> getCartList(Member member);


    boolean isExistCart(Long cartId);

    boolean isExistCartDetail(Long cartDetailId);
}
