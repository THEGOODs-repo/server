package com.umc.thegoods.service;

import com.umc.thegoods.domain.member.Member;
import com.umc.thegoods.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberTempServiceImpl implements MemberTempService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id).get();
    }
}
