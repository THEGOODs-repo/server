package com.umc.TheGoods.converter.Member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.PhoneAuth;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.web.dto.Member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.Member.MemberResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(String jwt) {
        return MemberResponseDTO.LoginResultDTO.builder()
                .jwt(jwt)
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDTO request, BCryptPasswordEncoder encoder) {
        return Member.builder()
                .nickname(request.getNickname())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .phone(request.getPhone())
                .memberRole(MemberRole.BUYER)
                .memberCategoryList(new ArrayList<>())
                .memberTermList(new ArrayList<>())
                .build();

    }

    public static List<MemberCategory> toMemberCategoryList(List<Category> categoryList) {


        return categoryList.stream()
                .map(category ->
                        MemberCategory.builder()
                                .category(category)
                                .build()
                ).collect(Collectors.toList());

    }

    public static List<MemberTerm> toMemberTermList(HashMap<Term, Boolean> termList) {

        return termList.entrySet().stream()
                .map(entry -> MemberTerm.builder()
                        .term(entry.getKey())
                        .memberAgree(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public static PhoneAuth toPhoneAuth(String phone, String code, Boolean expired) {

        return PhoneAuth.builder()
                .phone(phone)
                .code(code)
                .expired(expired)
                .build();
    }

    public static MemberResponseDTO.PhoneAuthSendResultDTO toPhoneAuthSendResultDTO(PhoneAuth phoneAuth) {
        return MemberResponseDTO.PhoneAuthSendResultDTO.builder()
                .phone(phoneAuth.getPhone())
                .authtoken(phoneAuth.getCode())
                .build();
    }

    public static MemberResponseDTO.PhoneAuthConfirmResultDTO toPhoneAuthConfirmResultDTO(Boolean check) {
        return MemberResponseDTO.PhoneAuthConfirmResultDTO.builder()
                .check(check)
                .build();
    }
}