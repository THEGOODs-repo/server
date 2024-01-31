package com.umc.TheGoods.service.MemberService;

import com.umc.TheGoods.domain.member.Member;

import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByNickname(String name);
}
