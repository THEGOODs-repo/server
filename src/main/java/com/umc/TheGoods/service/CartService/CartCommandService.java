package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;

public interface CartCommandService {

    void addCart(CartRequestDTO.cartAddDTOList request, Member member);
//
//    List<CartDetail> updateCart(CartRequestDTO.cartUpdateDTOList request, Member member);
//
//    void deleteCartDetail(CartRequestDTO.cartDetailDeleteDTO request, Member member);
//
//    void deleteCart(CartRequestDTO.cartDeleteDTO request, Member member);

}
