package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTag;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {
    List<MemberTag> findAllByMember(Member member);

    void deleteByMember(Member member);
}
