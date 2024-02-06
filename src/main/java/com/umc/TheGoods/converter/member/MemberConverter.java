package com.umc.TheGoods.converter.member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
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
                .itemList(new ArrayList<>())
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

    public static Auth toPhoneAuth(String phone, String code, Boolean expired) {

        return Auth.builder()
                .phone(phone)
                .code(code)
                .expireDate(LocalDateTime.now().plusMinutes(5))
                .expired(expired)
                .build();
    }

    public static MemberResponseDTO.PhoneAuthSendResultDTO toPhoneAuthSendResultDTO(Auth auth) {
        return MemberResponseDTO.PhoneAuthSendResultDTO.builder()
                .phone(auth.getPhone())
                .authCode(auth.getCode())
                .build();
    }

    public static MemberResponseDTO.PhoneAuthConfirmResultDTO toPhoneAuthConfirmResultDTO(Boolean checkPhone) {
        return MemberResponseDTO.PhoneAuthConfirmResultDTO.builder()
                .checkPhone(checkPhone)
                .build();
    }

    public static MemberResponseDTO.EmailDuplicateConfirmResultDTO toEmailDuplicateConfirmResultDTO(Boolean checkEmail) {
        return MemberResponseDTO.EmailDuplicateConfirmResultDTO.builder()
                .checkEmail(checkEmail)
                .build();
    }

    public static MemberResponseDTO.NicknameDuplicateConfirmResultDTO toNicknameDuplicateConfirmResultDTO(Boolean checkNickname) {
        return MemberResponseDTO.NicknameDuplicateConfirmResultDTO.builder()
                .checkNickname(checkNickname)
                .build();
    }


    public static MemberResponseDTO.PhoneAuthConfirmFindEmailResultDTO toPhoneAuthConfirmFindEmailDTO(String email) {
        return MemberResponseDTO.PhoneAuthConfirmFindEmailResultDTO.builder()
                .email(email)
                .build();
    }

    public static MemberResponseDTO.EmailAuthSendResultDTO toEmailAuthSendResultDTO(Auth auth) {
        return MemberResponseDTO.EmailAuthSendResultDTO.builder()
                .email(auth.getEmail())
                .authCode(auth.getCode())
                .build();
    }

    public static Auth toEmailAuth(String email, String code, Boolean expired) {

        return Auth.builder()
                .email(email)
                .code(code)
                .expireDate(LocalDateTime.now().plusMinutes(5))
                .expired(expired)
                .build();
    }

    public static MemberResponseDTO.EmailAuthConfirmResultDTO toEmailAuthConfirmResultDTO(Boolean checkEmail) {


        return MemberResponseDTO.EmailAuthConfirmResultDTO.builder()
                .checkEmail(checkEmail)
                .build();
    }

    public static MemberResponseDTO.SocialLoginResultDTO toSocialLoginResultDTO(String result) {

        return MemberResponseDTO.SocialLoginResultDTO.builder()
                .result(result)
                .build();
    }

    public static MemberResponseDTO.SocialJoinResultDTO toSocialJoinResultDTO(String phone, String email) {

        return MemberResponseDTO.SocialJoinResultDTO.builder()
                .phone(phone)
                .email(email)
                .build();
    }
}
