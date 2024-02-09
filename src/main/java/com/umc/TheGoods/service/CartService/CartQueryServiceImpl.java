package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.repository.cart.CartDetailRepository;
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
    private final CartDetailRepository cartDetailRepository;

    @Override
    public List<Cart> getCartList(Member member) {
        return cartRepository.findAllByMember(member);
    }


    @Override
    public boolean isExistCart(Long cartId) {
        return cartRepository.existsById(cartId);
    }

    @Override
    public boolean isExistCartDetail(Long cartDetailId) {
        return cartDetailRepository.existsById(cartDetailId);
    }
}
