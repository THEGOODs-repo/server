package com.umc.TheGoods.web.dto.Member;

import com.umc.TheGoods.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {
    private String nickname;
    private String password;
    private String email;
    private Date birthday;
    private String phone;
    private Gender gender;
}
