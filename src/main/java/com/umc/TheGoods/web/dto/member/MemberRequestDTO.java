package com.umc.TheGoods.web.dto.member;

import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.validation.annotation.ExistCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String nickname;
        private String password;
        private String email;
        private Date birthday;
        private String phone;
        private Gender gender;
        private List<Boolean> memberTerm;//약관 동의
        @ExistCategory
        private List<Long> memberCategory;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomInfoDTO{
        private List<Long> TagList;
        private List<Long> CategoryList;
        private Boolean InfoTerm;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String email;
        private String password;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthDTO {
        private String phone;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthConfirmDTO {
        private String phone;
        private String code;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailDuplicateConfirmDTO {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameDuplicateConfirmDTO {
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthConfirmFindEmailDTO {
        private String phone;
        private String code;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAuthDTO {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAuthConfirmDTO {
        private String email;
        private String code;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordUpdateDTO {
        private String password;
        private String checkPassword;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNameUpdateDTO {
        private String name;
        private String phone;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDTO {

        private String addressName;
        private String addressSpec;
        private String deliveryMemo;
        private String zipcode;
        private String recipientName;
        private String recipientPhone;
        private Boolean defaultCheck;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDTO {

        private String owner;
        private String bankName;
        private String accountNum;
        private Boolean defaultCheck;
    }

    @Getter
    @Setter
    public static class RefreshTokenDTO {
        String refreshToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WithdrawReasonDTO {
        Integer reason;
        Boolean caution;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeclareDTO{
        //접수 항목
        Integer receipt;
        //판매 항목
        String salePost;
        //신고 이유
        String reason;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactDTO{
        Integer start;
        Integer end;
        boolean all;
    }



}
