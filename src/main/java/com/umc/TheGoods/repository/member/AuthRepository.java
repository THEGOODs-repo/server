package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.member.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByPhone(String phone);

    Optional<Auth> findByEmail(String email);
}
