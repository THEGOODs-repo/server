package com.umc.TheGoods.converter.cart;

import com.umc.TheGoods.converter.item.ItemConverter;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.domain.order.CartDetail;
import com.umc.TheGoods.web.dto.cart.CartResponseDTO;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static CartResponseDTO.cartViewListDTO toCartViewListDTO(List<Cart> cartList) {
        return CartResponseDTO.cartViewListDTO.builder()
                .cartViewDTOList(cartList.stream().map(CartConverter::toCartViewDTO).collect(Collectors.toList()))
                .build();
    }

    public static CartResponseDTO.cartViewDTO toCartViewDTO(Cart cart) {
        List<CartResponseDTO.cartDetailViewDTO> cartDetailViewDTOList = cart.getCartDetailList().stream()
                .map(CartConverter::toCartDetailViewDTO).collect(Collectors.toList());

        List<ItemResponseDTO.ItemImgResponseDTO> itemImgResponseDTOList = cart.getItem().getItemImgList().stream()
                .map(ItemConverter::getItemImgDTO)
                .filter(ItemResponseDTO.ItemImgResponseDTO::getIsThumbNail).collect(Collectors.toList());

        String itemImgUrl = itemImgResponseDTOList.get(0).getItemImgUrl();

        return CartResponseDTO.cartViewDTO.builder()
                .cartId(cart.getId())
                .sellerName(cart.getItem().getMember().getNickname())
                .itemId(cart.getItem().getId())
                .itemName(cart.getItem().getName())
                .itemImg(itemImgUrl)
                .deliveryFee(cart.getItem().getDeliveryFee())
                .cartDetailViewDTOList(cartDetailViewDTOList)
                .build();
    }
    
    public static CartResponseDTO.cartDetailViewDTO toCartDetailViewDTO(CartDetail cartDetail) {
        return CartResponseDTO.cartDetailViewDTO.builder()
                .cartDetailId(cartDetail.getId())
                .optionId(cartDetail.getItemOption() == null ? null : cartDetail.getItemOption().getId())
                .optionName(cartDetail.getItemOption() == null ? null : cartDetail.getItemOption().getName()) // 옵션이 있는 경우에만 옵션 이름 설정
                .price(cartDetail.getItemOption() == null ? cartDetail.getCart().getItem().getPrice() : cartDetail.getItemOption().getPrice())
                .amount(cartDetail.getAmount())
                .build();
    }
}
