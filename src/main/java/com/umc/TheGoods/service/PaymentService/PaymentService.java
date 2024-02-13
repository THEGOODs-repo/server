package com.umc.TheGoods.service.PaymentService;

import com.siot.IamportRestClient.response.IamportResponse;
import com.umc.TheGoods.domain.payment.Payment;
import com.umc.TheGoods.web.dto.payment.PaymentCallbackRequestDTO;
import com.umc.TheGoods.web.dto.payment.PaymentRequestDTO;

public interface PaymentService {

    Long getTotalPrice(Long paymentId);

    // 결제 요청 데이터 조회
    PaymentRequestDTO findRequestDto(String orderUid);

    // 결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequestDTO request);
}
