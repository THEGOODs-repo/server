package com.umc.TheGoods.service.PaymentService;

import com.umc.TheGoods.domain.payment.Payment;
import com.umc.TheGoods.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl {

//    private final IamportClient iamportClient;


//    @Value("${iamport.key}")
//    private String restApiKey;
//
//    @Value("${iamport.secret}")
//    private String restApiSecret;
//
//    public PaymentServiceImpl() {
//        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
//    }

//    public ResponseEntity<String> processPayment(PaymentRequestDTO requestDto) {
//        PaymentRequestDTO paymentRequest = PaymentRequestDTO.builder()
//                .amount(requestDto.getAmount())
//                .pgProvider("html5_inicis")
//                .merchantUid(requestDto.getOrderUid())
//                .build();
//
//        try {
//            IamportResponse<Payment> response = iamportClient.paymentByMerchantUid(paymentRequest);
//            // 결제 성공 시 처리
//            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
//        } catch (IamportResponseException | IOException e) {
//            // 결제 실패 시 처리
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제에 실패하였습니다.");
//        }
//    }

    private PaymentRepository paymentRepository;

    public Long getTotalPrice(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            return payment.getTotalPrice();
        } else {
            throw new EntityNotFoundException("Payment not found with id: " + paymentId);
        }
    }


}
