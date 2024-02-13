package com.umc.TheGoods.web.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @JsonProperty("imp_uid")
    private String impUid;

    @JsonProperty("merchant_uid")
    private String merchantUid;

    private String orderUid;
    private String itemName;
    private String buyerName;
    private Long paymentTotalPrice;
    private String buyerEmail;
    private String buyerAddress;
}
