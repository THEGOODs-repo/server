package com.umc.TheGoods.service.Member;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.Member.MemberConverter;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.repository.CategoryRepository;
import com.umc.TheGoods.repository.MemberRepository;
import com.umc.TheGoods.repository.TermRepository;
import com.umc.TheGoods.web.dto.Member.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.TheGoods.config.springSecurity.utils.JwtUtil.createJwt;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final BCryptPasswordEncoder encoder; // 비밀번호 암호화
    private final TermRepository termRepository;

    @Value("${jwt.token.secret}")
    private String key; // 토큰 만들어내는 key값
    private int expiredMs = 1000 * 60 * 60 * 24 * 5;// 토큰 만료 시간 1일

    /**
     * 회원가입 api
     *
     * @param
     * @return
     */
    @Transactional
    public Member join(MemberRequestDTO.JoinDTO request) {

        // userNAme 중복 체크
        memberRepository.findByNickname(request.getNickname())
                .ifPresent(user -> {
                    throw new MemberHandler(ErrorStatus.MEMBER_NICNAME_DUPLICATED);
                });

        //저장
        Member member = MemberConverter.toMember(request, encoder);

        // 카테고리 저장 로직
        List<Category> categoryList = request.getMemberCategory().stream()
                .map(category -> {
                    return categoryRepository.findById(category).orElseThrow(() -> new MemberHandler(ErrorStatus.CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());

        List<MemberCategory> memberCategoryList = MemberConverter.toMemberCategoryList(categoryList);

        memberCategoryList.forEach(memberCategory -> {
            memberCategory.setMember(member);
        });

        // 약관동의 저장 로직
        HashMap<Term, Boolean> termMap = new HashMap<>();
        for (int i = 0; i < request.getMemberTerm().size(); i++) {
            termMap.put(termRepository.findById(i + 1L).orElseThrow(() -> new MemberHandler(ErrorStatus.CATEGORY_NOT_FOUND)), request.getMemberTerm().get(i));
        }

        List<MemberTerm> memberTermList = MemberConverter.toMemberTermList(termMap);
        memberTermList.forEach(memberTerm -> {
            memberTerm.setMember(member);
        });
        memberRepository.save(member);
        return member;
    }

    /**
     * 로그인 api
     *
     * @return
     */
    public String login(MemberRequestDTO.LoginDTO request) {

        //email 없음
        Member selectedMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_EMAIL_NOT_FOUND));


        //password 틀림
        if (!encoder.matches(request.getPassword(), selectedMember.getPassword())) {
            throw new MemberHandler(ErrorStatus.MEMBER_PASSWORD_ERROR);
        }

        //사용자 권한
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        return createJwt(selectedMember.getId(), selectedMember.getNickname(), expiredMs, key, roles);

    }

    /**
     * 카테고리 validator
     */
    @Override
    public boolean existCategoryById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}

