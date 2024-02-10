package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.domain.Payment;
import com.umc.TheGoods.web.dto.payment.PaymentRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Tag(name = "Payment", description = "결제(포트원) 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/payment/prepare")
    public void preparePayment(@RequestBody PaymentRequestDTO request)
            throws IamportResponseException, IOException {
        paymentService.postPrepare(request);
    }

    @PostMapping("/payments")
    public IamportResponse<Payment> paymentByImpUid(@RequestBody PaymentRequestDTO request) throws
            IamportResponseException,
            IOException {
        log.info("Payment Request : {}", request.toString());
        return iamportClient.paymentByImpUid(request.impUid());
    }
}
