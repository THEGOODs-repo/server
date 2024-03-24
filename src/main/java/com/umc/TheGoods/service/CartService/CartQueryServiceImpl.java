package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
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

    @Override
    public Cart getCartById(Long cartId, Member member) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_NOT_FOUND));

        // 해당 cart 내역을 수정할 권한 있는지 검증
        if (!cart.getMember().equals(member)) {
            throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
        }

        return cart;
    }


}
