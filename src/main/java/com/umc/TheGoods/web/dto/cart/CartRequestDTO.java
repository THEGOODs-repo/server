package com.umc.TheGoods.web.dto.cart;

import com.umc.TheGoods.validation.annotation.ExistCartDetail;
import com.umc.TheGoods.validation.annotation.ExistItem;
import com.umc.TheGoods.validation.annotation.ExistItemOption;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CartRequestDTO {

    @Getter
    public static class cartAddDTO {
        @NotNull
        @ExistItem
        Long itemId;

        Integer amount;

        @NotNull
        @Valid
        List<cartOptionAddDTO> cartOptionAddDTOList;

    }

    @Getter
    public static class cartOptionAddDTO {
        @NotNull
        @ExistItemOption
        Long itemOptionId;

        @Min(1)
        @Max(100000)
        @NotNull
        Integer amount;

    }

    @Getter
    public static class cartUpdateDTOList {
        @NotNull
        @Valid
        List<cartUpdateDTO> cartUpdateDTOList;
    }

    @Getter
    public static class cartUpdateDTO {
        @NotNull
        @ExistCartDetail
        Long cartDetailId;

        @Min(1)
        @Max(100000)
        @NotNull
        Integer amount;
    }

    @Getter
    public static class cartDetailDeleteDTO {
        @NotNull
        List<Long> cartDetailIdList;
    }

    @Getter
    public static class cartDeleteDTO {
        @NotNull
        List<Long> cartIdList;
    }

}
