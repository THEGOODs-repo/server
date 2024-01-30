package com.umc.TheGoods.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MemberDetail {
    Long memberId;
    String memberName;
    List<String> memberRole;
}
