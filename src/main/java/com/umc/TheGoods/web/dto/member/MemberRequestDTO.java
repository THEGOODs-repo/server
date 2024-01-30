package com.umc.TheGoods.web.dto.member;

import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.validation.annotation.ExistCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String email;
        private String password;
    }
}
