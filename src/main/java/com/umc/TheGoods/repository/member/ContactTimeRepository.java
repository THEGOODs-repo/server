package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.ContactTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTimeRepository extends JpaRepository<ContactTime, Long> {

    void deleteByMember(Member member);
}
