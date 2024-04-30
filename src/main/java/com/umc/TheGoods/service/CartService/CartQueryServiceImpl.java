package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.enums.CartStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;

    @Override
    public boolean isExistCart(Long cartId) {
        return cartRepository.existsById(cartId);
    }

    @Override
    public List<Cart> getCartsByItem(Long itemId, Member member) {
        return cartRepository.findAllByMemberAndItemIdAndCartStatus(member, itemId, CartStatus.ACTIVE);
    }

    @Override
    public List<Cart> getCartsByMember(Member member) {
        return cartRepository.findAllByMemberAndCartStatusOrderByItemIdAsc(member, CartStatus.ACTIVE);
    }


}
