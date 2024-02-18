package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.CartDetail;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;

import java.util.List;

public interface CartCommandService {

    void addCart(CartRequestDTO.cartAddDTO request, Member member);

    List<CartDetail> updateCart(CartRequestDTO.cartUpdateDTOList request, Member member);

    void deleteCartDetail(CartRequestDTO.cartDetailDeleteDTO request, Member member);

    void deleteCart(CartRequestDTO.cartDeleteDTO request, Member member);

}
