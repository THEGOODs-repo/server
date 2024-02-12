package com.umc.TheGoods.web.dto.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentRequestDTO {

    private String orderUid;
    private String itemName;
    private String buyerName;
    private Long paymentPrice;
    private String buyerEmail;
    private String buyerAddress;

    @Builder
    public PaymentRequestDTO(String orderUid, String itemName, String buyerName, Long paymentPrice, String buyerEmail, String buyerAddress) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
    }
}
