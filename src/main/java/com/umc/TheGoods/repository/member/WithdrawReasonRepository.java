package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.mypage.WithdrawReason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawReasonRepository extends JpaRepository<WithdrawReason, Long> {
    Optional<WithdrawReason> findByMember_Id(Long id);
}
