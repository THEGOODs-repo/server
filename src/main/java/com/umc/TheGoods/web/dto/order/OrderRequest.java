package com.umc.TheGoods.web.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class OrderRequest {
    @Schema(description = "주문 등록 요청 DTO")
    @Getter
    public static class OrderAddDto {
        @Schema(description = "주문자명")
        String name;

        @Schema(description = "연락처")
        String phone;

        @Schema(description = "우편번호")
        String zipcode;

        @Schema(description = "기본 주소")
        String address;

        @Schema(description = "상세 주소")
        String addressDetail;

        @Schema(description = "결제 유형", allowableValues = {"ACCOUNT", "CARD"})
        String payType;

        @Schema(description = "환불 계좌 은행")
        String refundBank;

        @Schema(description = "환불 계좌 번호")
        String refundAccount;

        @Schema(description = "환불 계좌 예금주명")
        String refundOwner;

        @Schema(description = "주문 상품 및 옵션, 수량 정보 리스트")
        List<OrderItemDto> orderItemDtoList;
    }

    @Schema(description = "주문 상품,옵션,수량 정보")
    @Getter
    public static class OrderItemDto {
        @Schema(description = "상품 id")
        Long itemId;

        @Schema(description = "상품 옵션 id")
        Optional<Long> itemOptionId;

        @Schema(description = "주문 수량")
        Integer amount;
    }
}
