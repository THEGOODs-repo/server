package com.umc.TheGoods.web.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.umc.TheGoods.domain.payment.Payment;
import com.umc.TheGoods.web.dto.payment.PaymentRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final IamportClient iamportClient;

    public PaymentController() {
        this.iamportClient = new IamportClient("${iamport.api.key}", "${iamport.api.secret}");
    }

    @PostMapping("/")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO requestDto) {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO().Builder()
                .amount(requestDto.getAmount())
                .pg("html5_inicis")
                .merchantUid(requestDto.getMerchantUid())
                .build();

        try {
            IamportResponse<Payment> response = iamportClient.paymentByMerchantUid(paymentRequest);
            // 결제 성공 시 처리
            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
        } catch (IamportResponseException | IOException e) {
            // 결제 실패 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제에 실패하였습니다.");
        }
    }
}
