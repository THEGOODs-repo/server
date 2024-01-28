package com.umc.TheGoods.service.Member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.member.PhoneAuth;
import com.umc.TheGoods.web.dto.Member.MemberRequestDTO;

public interface MemberCommandService {

    boolean existCategoryById(Long categoryId);

    Member join(MemberRequestDTO.JoinDTO request);

    String login(MemberRequestDTO.LoginDTO request);

    PhoneAuth sendPhoneAuth(String phone) throws JsonProcessingException;

    Boolean confirmPhoneAuth(MemberRequestDTO.PhoneAuthConfirmDTO request);


}
