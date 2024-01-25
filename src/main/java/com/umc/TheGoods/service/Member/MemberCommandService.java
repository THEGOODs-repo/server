package com.umc.TheGoods.service.Member;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.enums.MemberStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.umc.TheGoods.config.springSecurity.utils.JwtUtil.createJwt;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder; // 비밀번호 암호화

    @Value("${jwt.token.secret}")
    private String key; // 토큰 만들어내는 key값
    private int expiredMs = 1000 * 60 * 60 * 24 * 5;// 토큰 만료 시간 1일

    public Member join(String nickname, String password, String email, Date birthday, Gender gender, String phone) {
        // userNAme 중복 체크
        memberRepository.findMemberByNickname(nickname)
                .ifPresent(user -> {
                    throw new MemberHandler(ErrorStatus.MEMBER_NICNAME_DUPLICATED);
                });
        //저장
        Member member = Member.builder()
                .nickname(nickname)
                .password(encoder.encode(password))
                .email(email)
                .birthday(birthday)
                .gender(gender)
                .phone(phone)
                .memberRole(MemberRole.BUYER)
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(member);
        return member;
    }


    public String login(String email, String password) {

        //email 없음
        Member selectedMember = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_EMAIL_NOT_FOUND));


        //password 틀림
        if (!encoder.matches(password, selectedMember.getPassword())) {
            throw new MemberHandler(ErrorStatus.MEMBER_PASSWORD_ERROR);
        }

        //사용자 권한
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        return createJwt(selectedMember.getId(), selectedMember.getNickname(), expiredMs, key, roles);


    }
}

