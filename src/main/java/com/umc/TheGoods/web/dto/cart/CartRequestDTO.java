package com.umc.TheGoods.web.dto.cart;

import com.umc.TheGoods.validation.annotation.ExistCart;
import com.umc.TheGoods.validation.annotation.ExistItem;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CartRequestDTO {

    @Getter
    public static class cartAddDTOList {
        @NotNull
        @Valid
        List<cartAddDTO> cartAddDTOList;

    }

    @Getter
    public static class cartAddDTO {
        @NotNull
        @ExistItem
        Long itemId;

        @Min(1)
        @Max(100000)
        @NotNull
        Integer amount;

        Long itemOptionId;


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
        @ExistCart
        Long cartId;

        @Min(1)
        @Max(100000)
        @NotNull
        Integer amount;
    }

    @Getter
    public static class cartOptionDeleteDTO {
        @NotNull
        List<Long> cartIdList;
    }

    @Getter
    public static class cartDeleteDTO {
        @NotNull
        List<Long> cartIdList;
    }

}
