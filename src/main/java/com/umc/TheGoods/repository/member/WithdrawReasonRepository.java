package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.mypage.WithdrawReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawReasonRepository extends JpaRepository<WithdrawReason, Long> {
}
