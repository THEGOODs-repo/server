package com.umc.TheGoods.web.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long amount; // 실제 결제된 금액
    private Long orderUid; // 주문 ID 또는 주문번호 등 식별자
}
