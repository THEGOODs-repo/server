package com.umc.TheGoods.web.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cartViewListDTO {
        List<cartViewDTO> cartViewDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cartViewDTO {
        Long cartId;
        String sellerName;
        Long itemId;
        String itemName;
        String itemImg;
        Integer deliveryFee;
        List<cartDetailViewDTO> cartDetailViewDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cartDetailViewDTO {
        Long cartDetailId;
        Long optionId;
        String optionName;
        Long price;
        Integer amount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cartStockDTO {
        Long itemId;
        List<cartDetailStockDTO> cartDetailStockDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cartDetailStockDTO {
        Long itemOptionId;
        Integer stock;
    }

}
