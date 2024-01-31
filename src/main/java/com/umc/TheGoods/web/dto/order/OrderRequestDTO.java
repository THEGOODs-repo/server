package com.umc.TheGoods.web.dto.order;

import com.umc.TheGoods.validation.annotation.OrderAvailable;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class OrderRequestDTO {
    @Getter
    public static class OrderAddDTO {

        @NotBlank
        String name;

        @NotBlank
        String phone;

        @Size(min = 5, max = 6)
        String zipcode;

        @NotBlank
        String address;

        String addressDetail;

        String deliveryMemo;

        @NotBlank
        String payType;

        @NotBlank
        String refundBank;

        @NotBlank
        String refundAccount;

        @NotBlank
        String refundOwner;

        String depositor;

        @NotEmpty
        @Valid
        List<OrderItemDTO> orderItemDTOList;
    }

    @Getter
    @OrderAvailable
    public static class OrderItemDTO {
        @NotNull
        Long itemId;

        @NotEmpty
        List<OrderDetailDTO> orderDetailDTOList;
    }

    @Getter
    public static class OrderDetailDTO {
        Long itemOptionId;

        @Min(1)
        @Max(100000)
        Integer amount;
    }
}
