package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.CartDetail;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;

public interface CartCommandService {

    void addCart(CartRequestDTO.cartAddDTO request, Member member);

    CartDetail updateCart(CartRequestDTO.cartUpdateDTO request, Member member);
}
