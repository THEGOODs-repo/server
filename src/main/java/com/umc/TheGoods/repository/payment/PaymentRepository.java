package com.umc.TheGoods.repository.payment;

import com.umc.TheGoods.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
