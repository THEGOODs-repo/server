package com.umc.TheGoods.converter.member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.mapping.member.MemberCategory;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.Term;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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

    public static Member toUpdatePassword(Member member, String password) {
        List<MemberTerm> memberTermList = member.getMemberTermList();
        List<MemberCategory> memberCategoryList = member.getMemberCategoryList();
        List<Item> memberItemList = member.getItemList();
        if (memberItemList == null) {
            memberItemList = new ArrayList<>();
        }

        if (memberTermList == null) {
            memberTermList = new ArrayList<>();
        }

        if (memberCategoryList == null) {
            memberCategoryList = new ArrayList<>();
        }
        return Member.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .password(password)
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .phone(member.getPhone())
                .memberRole(MemberRole.BUYER)
                .memberCategoryList(memberCategoryList)
                .memberTermList(memberTermList)
                .itemList(memberItemList)
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


    public static MemberResponseDTO.PhoneAuthConfirmFindEmailResultDTO toPhoneAuthConfirmFindEmailDTO(String email,String url) {
        return MemberResponseDTO.PhoneAuthConfirmFindEmailResultDTO.builder()
                .email(email)
                .url(url)
                .build();
    }

    public static MemberResponseDTO.EmailAuthSendResultDTO toEmailAuthSendResultDTO(Auth auth) {
        return MemberResponseDTO.EmailAuthSendResultDTO.builder()
                .email(auth.getEmail())
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

    public static MemberResponseDTO.EmailAuthConfirmResultDTO toEmailAuthConfirmResultDTO(Boolean checkEmail, String jwt) {


        return MemberResponseDTO.EmailAuthConfirmResultDTO.builder()
                .checkEmail(checkEmail)
                .jwt(jwt)
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

    public static MemberResponseDTO.ProfileModifyResultDTO toProfileModify(Member member) {

        return MemberResponseDTO.ProfileModifyResultDTO.builder()
                .nickname(member.getNickname())
                .build();
    }

    public static ProfileImg toProfileImg(String url, Member member) {
        return ProfileImg.builder()
                .url(url)
                .member(member)
                .build();
    }

    public static MemberResponseDTO.ProfileResultDTO toProfile(Member member, String url, List<Account> account, List<Address> address) {

        List<MemberResponseDTO.AccountDTO> accountList = account.stream().map(a -> MemberResponseDTO.AccountDTO.builder()
                .id(a.getId())
                .accountNum(a.getAccountNum())
                .bankName(a.getBankName())
                .owner(a.getOwner())
                .defaultCheck(a.getDefaultCheck())
                .build()).collect(Collectors.toList());
        List<MemberResponseDTO.AddressDTO> addressList = address.stream().map(a -> MemberResponseDTO.AddressDTO.builder()
                .addressName(a.getAddressName())
                .addressSpec(a.getAddressSpec())
                .deliveryMemo(a.getDeliveryMemo())
                .defaultCheck(a.getDefaultCheck())
                .recipientName(a.getRecipientName())
                .recipientPhone(a.getRecipientPhone())
                .id(a.getId())
                .zipcode(a.getZipcode()).build()).collect(Collectors.toList());

        if (address.isEmpty() && account.isEmpty()){
            return MemberResponseDTO.ProfileResultDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .url(url)
                    .accountList(null)
                    .addressList(null)
                    .build();
        }
        if (account.isEmpty()){
            return MemberResponseDTO.ProfileResultDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .url(url)
                    .addressList(addressList)
                    .accountList(accountList)
                    .build();
        }
        if(address.isEmpty()){
            return MemberResponseDTO.ProfileResultDTO.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .url(url)
                    .addressList(null)
                    .accountList(accountList)
                    .build();
        }

        return MemberResponseDTO.ProfileResultDTO.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .url(url)
                .addressList(addressList)
                .accountList(accountList)
                .build();
    }

    public static MemberResponseDTO.PasswordUpdateResultDTO toPasswordUpdateResultDTO(boolean updatePassword) {
        return MemberResponseDTO.PasswordUpdateResultDTO.builder()
                .updatePassword(updatePassword)
                .build();
    }

    public static Member toUpdateProfile(Member member, ProfileImg profileImg, String nickname, String introduce) {
        List<MemberTerm> memberTermList = member.getMemberTermList();
        List<MemberCategory> memberCategoryList = member.getMemberCategoryList();
        List<Item> memberItemList = member.getItemList();
        if (memberItemList == null) {
            memberItemList = new ArrayList<>();
        }

        if (memberTermList == null) {
            memberTermList = new ArrayList<>();
        }

        if (memberCategoryList == null) {
            memberCategoryList = new ArrayList<>();
        }
        return Member.builder()
                .id(member.getId())
                .nickname(nickname)
                .password(member.getPassword())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .phone(member.getPhone())
                .memberRole(MemberRole.BUYER)
                .memberCategoryList(memberCategoryList)
                .memberTermList(memberTermList)
                .itemList(memberItemList)
                .introduce(introduce)
                .profileImg(profileImg)
                .build();
    }

    public static MemberResponseDTO.RoleUpdateResultDTO toUpdateRole(Member member) {

        return MemberResponseDTO.RoleUpdateResultDTO.builder()
                .role(member.getMemberRole())
                .build();
    }

    public static MemberResponseDTO.PhoneNameUpdateResultDTO toUpdatePhoneName(Member member) {

        return MemberResponseDTO.PhoneNameUpdateResultDTO.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }

    public static Address toAddress(MemberRequestDTO.AddressDTO request,Member member){
        return Address.builder()
                .addressName(request.getAddressName())
                .addressSpec(request.getAddressSpec())
                .deliveryMemo(request.getDeliveryMemo())
                .zipcode(request.getZipcode())
                .recipientName(request.getRecipientName())
                .recipientPhone(request.getRecipientPhone())
                .defaultCheck(request.getDefaultCheck())
                .member(member)
                .build();
    }


    public static Account toAccount(MemberRequestDTO.AccountDTO request, Member member){
            return Account.builder()
                    .accountNum(request.getAccountNum())
                    .bankName(request.getBankName())
                    .owner(request.getOwner())
                    .defaultCheck(request.getDefaultCheck())
                    .member(member)
                    .build();
    }


    public static MemberResponseDTO.AddressResultDTO toPostAddressDTO(String address){

        return MemberResponseDTO.AddressResultDTO.builder()
                .name(address)
                .build();

    }

    public static MemberResponseDTO.AccountResultDTO toPostAccountDTO(String account){

        return MemberResponseDTO.AccountResultDTO.builder()
                .name(account)
                .build();

    }

    public static MemberResponseDTO.AddressResultDTO toUpdateAddressDTO(String address){

        return MemberResponseDTO.AddressResultDTO.builder()
                .name(address)
                .build();

    }

    public static MemberResponseDTO.AccountResultDTO toUpdateAccountDTO(String account){

        return MemberResponseDTO.AccountResultDTO.builder()
                .name(account)
                .build();

    }

}
