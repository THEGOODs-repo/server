package com.umc.TheGoods.web.dto.item;

import com.umc.TheGoods.domain.enums.ItemStatus;
import com.umc.TheGoods.domain.types.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadItemResultDTO {
        Long itemId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemContentDTO {
        Long itemId;
        ItemStatus status;
        Integer stock;
        Long price;
        Integer deliveryFee;
        DeliveryType deliveryType;
        Integer deliveryDate;
        String description;
        Boolean isLimitless;
        LocalDate startDate;
        LocalDate endDate;
        String category;
        Long viewCount;
        Long dibsCount;
        Long salesCount;
        List<ItemImgResponseDTO> itemImgUrlList;
        List<ItemOptionResponseDTO> itemOptionList;
        List<String> itemTag;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemImgResponseDTO {
        Boolean isThumbNail;
        String itemImgUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemOptionResponseDTO {
        Long itemOptionId;
        String name;
        Long price;
        Integer stock;
    }
}
