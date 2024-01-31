package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.member.PhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {
    Optional<PhoneAuth> findByPhone(String phone);
}
