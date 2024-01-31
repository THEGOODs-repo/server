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
    public static class OrderAddResultDto {
        Long orderId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderPreViewListDTO {
        List<OrderPreViewDTO> orderPreViewDTOList;
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
    public static class OrderPreViewDTO {
        Long orderDetailId;
        LocalDateTime orderDateTime;
        String imgUrl;
        OrderStatus orderStatus;
        String itemName;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String optionName;
        Integer amount;
        Long price;
    }

}
