package com.umc.TheGoods.web.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umc.TheGoods.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderAddResultDTO {
        Long orderId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class orderItemPreViewListDTO {
        List<OrderItemPreViewDTO> orderItemPreViewDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemPreViewDTO {
        Long orderItemId;
        LocalDateTime orderDateTime;
        String imgUrl;
        OrderStatus orderStatus;
        String itemName;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String optionString;
        Long price;
    }

}
