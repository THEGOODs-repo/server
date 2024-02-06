package com.umc.TheGoods.web.dto.cart;

import com.umc.TheGoods.validation.annotation.ExistCart;
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
    public static class cartUpdateDTO {
        @NotNull
        @ExistCart
        Long cartId;

        @Min(1)
        @Max(100000)
        @NotNull
        Integer amount;
    }

}
