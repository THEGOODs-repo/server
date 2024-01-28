package com.umc.TheGoods.service.Member;

import com.umc.TheGoods.domain.member.Member;

import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMemberById(Long id);
}
