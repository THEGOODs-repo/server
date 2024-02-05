package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;


    @Override
    public boolean isExistCart(Long cartId) {
        return cartRepository.existsById(cartId);
    }
}
