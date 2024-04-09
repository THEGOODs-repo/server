package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;

public interface CartCommandService {

    void addCart(CartRequestDTO.cartAddDTOList request, Member member);

    void updateCart(CartRequestDTO.cartUpdateDTOList request, Member member);

    void deleteCart(CartRequestDTO.cartOptionDeleteDTO request, Member member);

    void deleteCartByItem(CartRequestDTO.cartDeleteByItemDTO request, Member member);

}
