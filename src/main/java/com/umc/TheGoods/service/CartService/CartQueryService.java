package com.umc.TheGoods.service.CartService;

public interface CartQueryService {

    boolean isExistCart(Long cartId);

    boolean isExistCartDetail(Long cartDetailId);
}
