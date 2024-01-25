package com.umc.thegoods.web.dto.order;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class OrderRequest {
    @Getter
    public static class OrderAddDto {
        String name;
        String phone;
        String zipcode;
        String address;
        String addressDetail;
        String payType;
        String refundBank;
        String refundAccount;
        String refundOwner;
        List<OrderItemDto> orderItemDtoList;
    }

    @Getter
    public static class OrderItemDto {
        Long itemId;
        Optional<Long> itemOptionId;
        Integer amount;
    }
}
