package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.service.PaymentService.PaymentServiceImpl;
import com.umc.TheGoods.web.dto.payment.PaymentRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Payment", description = "결제(포트원) 관련 API")
@Validated
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @Autowired
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO requestDto) {
        return paymentService.processPayment(requestDto);
    }

    /*
    private final IamportClient iamportClient;

    public PaymentController() {
        this.iamportClient = new IamportClient("${iamport.api.key}", "${iamport.api.secret}");
    }

    @PostMapping("/")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO requestDto) {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO().Builder()
                .amount(requestDto.getPaymentTotalPrice())
                .pg("html5_inicis")
                .merchantUid(requestDto.getOrderUid())
                .build();

        try {
            IamportResponse<Payment> response = iamportClient.paymentByImpUid(paymentRequest);
            // 결제 성공 시 처리
            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
        } catch (IamportResponseException | IOException e) {
            // 결제 실패 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제에 실패하였습니다.");
        }
    }
     */

    /*
    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentCompleteRequest request) {
        try {
            // 요청 바디에서 imp_uid, merchant_uid 추출
            String impUid = request.getImpUid();
            String merchantUid = request.getMerchantUid();

            // 여기에 결제 정보 검증 및 추가 로직을 구현합니다.

            // 처리가 완료되면 성공 응답 반환
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 발생 시 실패 응답 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
     */
}
