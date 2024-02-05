package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;

public interface CartCommandService {

    void addCart(CartRequestDTO.cartAddDTO request, Member member);

    Cart updateCart(CartRequestDTO.cartUpdateDTO request, Member member);
}
