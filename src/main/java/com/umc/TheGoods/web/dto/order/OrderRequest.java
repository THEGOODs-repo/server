package com.umc.TheGoods.web.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Optional;

public class OrderRequest {
    @Schema(description = "주문 등록 요청 DTO")
    @Getter
    public static class OrderAddDto {
        @Schema(description = "주문자명")
        @NotBlank
        String name;

        @Schema(description = "연락처")
        @NotBlank
        String phone;

        @Schema(description = "우편번호")
        @Size(min = 5, max = 6)
        String zipcode;

        @Schema(description = "기본 주소")
        @NotBlank
        String address;

        @Schema(description = "상세 주소")
        String addressDetail;

        @Schema(description = "결제 유형", allowableValues = {"ACCOUNT", "CARD"})
        @NotBlank
        String payType;

        @Schema(description = "환불 계좌 은행")
        @NotBlank
        String refundBank;

        @Schema(description = "환불 계좌 번호")
        @NotBlank
        String refundAccount;

        @Schema(description = "환불 계좌 예금주명")
        @NotBlank
        String refundOwner;

        @Schema(description = "주문 상품 및 옵션, 수량 정보 리스트")
        @NotEmpty
        @Valid
        List<OrderItemDto> orderItemDtoList;
    }

    @Schema(description = "주문 상품,옵션,수량 정보")
    @Getter
    public static class OrderItemDto {
        @Schema(description = "상품 id")
        @NotNull
        Long itemId;

        @Schema(description = "상품 옵션 id")
        Optional<Long> itemOptionId;

        @Schema(description = "주문 수량")
        @Min(1)
        @Max(100000)
        Integer amount;
    }
}
