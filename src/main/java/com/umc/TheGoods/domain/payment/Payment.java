package com.umc.TheGoods.domain.payment;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.domain.order.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member buyer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", unique = true)
    private Orders orders;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private String paymentUid;  // 결제 고유 번호 (imp_uid)

    @Column(nullable = false)
    private String paymentMethod; // 결제 수단

    @Column(nullable = false)
    private String pgProvider; // PG사 정보

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @Builder
    public Payment(Long price, PaymentStatus status) {
        this.price = price;
        this.status = status;
    }

    public void changePaymentBySuccess(PaymentStatus status, String paymentUid) {
        this.status = status;
        this.paymentUid = paymentUid;
    }

    public Long getTotalPrice() {
        Long totalPrice = 0L;

        // 주문 정보 확인
        Orders order = this.orders;
        if (order != null) {
            // 주문 상품 정보 확인
            List<OrderItem> orderItems = order.getOrderItem();
            for (OrderItem orderItem : orderItems) {
                totalPrice += orderItem.getTotalPrice(); // 주문 상품 가격 합산
            }

            // 배송비 추가
            totalPrice += order.getDeliveryFee();
        }

        return totalPrice;
    }

    public void setBuyer(Member buyer) {
        this.buyer = buyer;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPgProvider(String pgProvider) {
        this.pgProvider = pgProvider;
    }

}
