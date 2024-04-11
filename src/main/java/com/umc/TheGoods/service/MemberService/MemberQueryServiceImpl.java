package com.umc.TheGoods.service.MemberService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.CategoryTag;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.domain.mypage.Declaration;
import com.umc.TheGoods.repository.TagRepository;
import com.umc.TheGoods.repository.item.CategoryTagRepository;
import com.umc.TheGoods.repository.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.MetaMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;
    private final ProfileImgRepository profileImgRepository;

    private final AddressRepository addressRepository;

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final DeclarationRepository declarationRepository;

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
    public List<Address> findAllAddressById(Long id) {

        List<Address> address = addressRepository.findAllByMember_Id(id);
        return address;
    }

    @Override
    public List<Account> findAllAccountById(Long id) {

        List<Account> account = accountRepository.findAllByMember_Id(id);
        return account;
    }

    public List<Account> findAccountById(Long id){
        return accountRepository.findAllByMember_Id(id);
    }





    @Override
    public List<Declaration> findDeclarationByMember(Member member) {

        List<Declaration> declarationList = declarationRepository.findAllByMember(member);

        return declarationList;
    }
}
