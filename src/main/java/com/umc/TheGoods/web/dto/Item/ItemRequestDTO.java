package com.umc.TheGoods.web.dto;

import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.ItemOption;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.List;

public class ItemRequestDTO {

    @Getter
    public static class UploadItemDTO{
        @NotBlank
        String name;
        @NotNull
        String status;
        @Nullable
        Integer stock;
        @Nullable
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
        @Nullable
        LocalDate startDate;
        @Nullable
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
        List<itemImgDTO> itemImgUrlList;
        @Nullable
        List<itemOptionDTO> itemOptionList;
        @NotNull
        List<Long> itemTag;
    }

    @Getter
    public static class itemImgDTO{
        @Nullable
        Boolean isThumbnail;
        @NotNull
        String imgUrl;
    }

    @Getter
    public static class itemOptionDTO{
        @NotNull
        String name;
        @NotNull
        Long price;
        @NotNull
        Integer stock;
    }
}
