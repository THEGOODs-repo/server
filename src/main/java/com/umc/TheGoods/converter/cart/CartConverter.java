package com.umc.TheGoods.converter.cart;

import com.umc.TheGoods.domain.enums.CartStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.order.Cart;

public class CartConverter {
    public static Cart toCartWithItemOption(Item item, ItemOption itemOption, Integer amount) {
        return Cart.builder()
                .item(item)
                .itemOption(itemOption)
                .amount(amount)
                .cartStatus(CartStatus.ACTIVE)
                .build();
    }

    public static Cart toCartWithoutItemOption(Item item, Integer amount) {
        return Cart.builder()
                .item(item)
                .amount(amount)
                .cartStatus(CartStatus.ACTIVE)
                .build();
    }

//    public static CartDetail toCartDetail(Integer amount) {
//        return CartDetail.builder()
//                .amount(amount)
//                .build();
//    }
//
//    public static CartResponseDTO.cartViewListDTO toCartViewListDTO(List<Cart> cartList) {
//        return CartResponseDTO.cartViewListDTO.builder()
//                .cartViewDTOList(cartList.stream().map(CartConverter::toCartViewDTO).collect(Collectors.toList()))
//                .build();
//    }
//
//    public static CartResponseDTO.cartViewDTO toCartViewDTO(Cart cart) {
//        List<CartResponseDTO.cartDetailViewDTO> cartDetailViewDTOList = cart.getCartDetailList().stream()
//                .map(CartConverter::toCartDetailViewDTO).collect(Collectors.toList());
//
//        List<ItemResponseDTO.ItemImgResponseDTO> itemImgResponseDTOList = cart.getItem().getItemImgList().stream()
//                .map(ItemConverter::getItemImgDTO)
//                .filter(ItemResponseDTO.ItemImgResponseDTO::getIsThumbNail).collect(Collectors.toList());
//
//        String itemImgUrl = itemImgResponseDTOList.get(0).getItemImgUrl();
//
//        return CartResponseDTO.cartViewDTO.builder()
//                .cartId(cart.getId())
//                .sellerName(cart.getItem().getMember().getNickname())
//                .itemId(cart.getItem().getId())
//                .itemName(cart.getItem().getName())
//                .itemImg(itemImgUrl)
//                .deliveryFee(cart.getItem().getDeliveryFee())
//                .cartDetailViewDTOList(cartDetailViewDTOList)
//                .build();
//    }
//
//    public static CartResponseDTO.cartDetailViewDTO toCartDetailViewDTO(CartDetail cartDetail) {
//        return CartResponseDTO.cartDetailViewDTO.builder()
//                .cartDetailId(cartDetail.getId())
//                .optionId(cartDetail.getItemOption() == null ? null : cartDetail.getItemOption().getId())
//                .optionName(cartDetail.getItemOption() == null ? null : cartDetail.getItemOption().getName()) // 옵션이 있는 경우에만 옵션 이름 설정
//                .price(cartDetail.getItemOption() == null ? cartDetail.getCart().getItem().getPrice() : cartDetail.getItemOption().getPrice())
//                .amount(cartDetail.getAmount())
//                .build();
//    }
//
//    public static CartResponseDTO.cartStockDTO toCartStockDTO(Cart cart) {
//
//        List<CartResponseDTO.cartDetailStockDTO> cartDetailStockDTOList = cart.getCartDetailList().stream()
//                .map(CartConverter::toCartDetailStockDTO).collect(Collectors.toList());
//
//        return CartResponseDTO.cartStockDTO.builder()
//                .itemId(cart.getItem().getId())
//                .cartDetailStockDTOList(cartDetailStockDTOList)
//                .build();
//
//    }
//
//    public static CartResponseDTO.cartDetailStockDTO toCartDetailStockDTO(CartDetail cartDetail) {
//        return CartResponseDTO.cartDetailStockDTO.builder()
//                .itemOptionId(cartDetail.getItemOption() == null ? null : cartDetail.getItemOption().getId())
//                .stock(cartDetail.getItemOption() == null ? cartDetail.getCart().getItem().getStock() : cartDetail.getItemOption().getStock())
//                .build();
//    }


}
