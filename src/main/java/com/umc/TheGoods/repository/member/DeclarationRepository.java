package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

    List<Declaration> findAllByMember(Member member);
}
