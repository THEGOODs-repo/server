package com.umc.TheGoods.converter.cart;

import com.umc.TheGoods.domain.enums.CartStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.web.dto.cart.CartResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

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


//    public static CartResponseDTO.cartViewListDTO toCartViewListDTO(List<Cart> cartList) {
//
//        List<CartResponseDTO.cartViewDTO> cartViewDTOList = new ArrayList<>();
//
//        cartList.forEach(cart -> {
//            if (!cartViewDTOList.isEmpty()) {
//                CartResponseDTO.cartViewDTO cartViewDTO = cartViewDTOList.get(cartViewDTOList.size() - 1);
//                if (cartViewDTO.getItemId().equals(cart.getItem().getId())) { // cartViewDTO 내의 cartOptionViewDTO 추가
//
//                } else { // 세로운 cartViewDTO 추가
//                    CartResponseDTO.cartOptionViewDTO cartOptionViewDTO = toCartOptionViewDTO(cart);
//                }
//            }
//
//        });
//
//        return CartResponseDTO.cartViewListDTO.builder()
//                .cartViewDTOList(cartList.stream().map(CartConverter::toCartViewDTO).collect(Collectors.toList()))
//                .build();
//    }

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
//    public static CartResponseDTO.cartOptionViewDTO toCartOptionViewDTO(Cart cart) {
//        return CartResponseDTO.cartOptionViewDTO.builder()
//                .cartId(cart.getId())
//                .optionId(cart.getItemOption() == null ? null : cart.getItemOption().getId())
//                .optionName(cart.getItemOption() == null ? null : cart.getItemOption().getName()) // 옵션이 있는 경우에만 옵션 이름 설정
//                .price(cart.getItemOption() == null ? cart.getItem().getPrice() : cart.getItemOption().getPrice())
//                .amount(cart.getAmount())
//                .build();
//    }
//
    public static CartResponseDTO.cartStockDTO toCartStockDTO(List<Cart> cartList) {

        List<CartResponseDTO.cartOptionStockDTO> cartOptionStockDTOList = cartList.stream()
                .map(CartConverter::toCartOptionStockDTO).collect(Collectors.toList());


        return CartResponseDTO.cartStockDTO.builder()
                .itemId(cartList.get(0).getItem().getId())
                .cartOptionStockDTOList(cartOptionStockDTOList)
                .build();

    }

    public static CartResponseDTO.cartOptionStockDTO toCartOptionStockDTO(Cart cart) {
        return CartResponseDTO.cartOptionStockDTO.builder()
                .itemOptionId(cart.getItemOption() == null ? null : cart.getItemOption().getId())
                .stock(cart.getItemOption() == null ? cart.getItem().getStock() : cart.getItemOption().getStock())
                .build();
    }


}
