package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}