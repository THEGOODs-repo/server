package com.umc.TheGoods.web.dto;

import com.umc.TheGoods.domain.item.Category;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class ItemRequestDTO {

    @Getter
    public static class UploadItemDTO{
        @NotBlank
        String name;
        @NotNull
        String status;
        Integer stock;
        Long price;
        @NotNull
        Integer deliveryFee;
        @NotNull
        Integer deliveryType;
        @NotNull
        Integer deliveryDate;
        @NotNull
        String description;
        @NotNull
        Boolean isLimitless;
        LocalDate startDate;
        LocalDate endDate;
        @NotNull
        Long viewCount;
        @NotNull
        Long dibsCount;
        @NotNull
        Long salesCount;
        @NotNull
        Long category;
        @NotNull
        List<Long> itemTag;
        @NotNull
        List<String> itemImgUrl;
    }
}
