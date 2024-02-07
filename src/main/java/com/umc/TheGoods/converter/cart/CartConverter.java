package com.umc.TheGoods.converter.cart;

import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.domain.order.CartDetail;

import java.util.ArrayList;

public class CartConverter {
    public static Cart toCart() {
        return Cart.builder()
                .cartDetailList(new ArrayList<>())
                .build();
    }

    public static CartDetail toCartDetail(Integer amount) {
        return CartDetail.builder()
                .amount(amount)
                .build();
    }

}
