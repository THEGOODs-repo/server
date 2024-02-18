package com.umc.TheGoods.service.MemberService;

import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.repository.member.AccountRepository;
import com.umc.TheGoods.repository.member.AddressRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import com.umc.TheGoods.repository.member.ProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;
    private final ProfileImgRepository profileImgRepository;

    private final AddressRepository addressRepository;

    private final AccountRepository accountRepository;

    @Override
    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findMemberByNickname(String name) {
        return memberRepository.findByNickname(name);
    }

    @Override
    public Optional<ProfileImg> findProfileImgByMember(Long id) {

        Optional<ProfileImg> profileImg = profileImgRepository.findByMember_Id(id);
        return profileImg;
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Optional<Address> findAddressById(Long id) {

        Optional<Address> address = addressRepository.findByMember_Id(id);
        return address;
    }

    @Override
    public Optional<Account> findAccountById(Long id) {

        Optional<Account> account = accountRepository.findByMember_Id(id);
        return account;
    }
}
