package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByMember_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.accountNum = :accountNum, a.bankName = :bankName, a.owner = :owner, a.defaultCheck = :defaultCheck WHERE a.id = :accountId")
    void changeAccount(Long accountId, String accountNum,String bankName, String owner, Boolean defaultCheck);
}
