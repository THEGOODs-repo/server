package com.umc.TheGoods.converter.cart;

import com.umc.TheGoods.domain.order.Cart;

public class CartConverter {
    public static Cart toCart(Integer amount) {
        return Cart.builder()
                .amount(amount)
                .build();
    }

}
