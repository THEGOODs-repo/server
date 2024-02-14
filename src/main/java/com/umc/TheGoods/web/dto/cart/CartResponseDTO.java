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
        String sellerName;
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
        String optionName;
        Long price;
        Integer amount;
    }
}