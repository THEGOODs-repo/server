package com.umc.TheGoods.service.MemberService;

import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByNickname(String name);

    Optional<ProfileImg> findProfileImgByMember(Long id);

    Optional<Member> findMemberByEmail(String email);

    List<Address> findAllAddressById(Long id);

    List<Account> findAllAccountById(Long id);
}
