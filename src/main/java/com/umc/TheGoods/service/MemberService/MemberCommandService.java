package com.umc.TheGoods.service.MemberService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;


public interface MemberCommandService {

    boolean existCategoryById(Long categoryId);

    Member join(MemberRequestDTO.JoinDTO request);

    String login(MemberRequestDTO.LoginDTO request);

    Auth sendPhoneAuth(String phone) throws JsonProcessingException;

    Boolean confirmPhoneAuth(MemberRequestDTO.PhoneAuthConfirmDTO request);

    Boolean confirmEmailDuplicate(MemberRequestDTO.EmailDuplicateConfirmDTO request);

    Boolean confirmNicknameDuplicate(MemberRequestDTO.NicknameDuplicateConfirmDTO request);

    String confirmPhoneAuthFindEmail(MemberRequestDTO.PhoneAuthConfirmFindEmailDTO request);

    Auth sendEmailAuth(String email);

    Boolean confirmEmailAuth(MemberRequestDTO.EmailAuthConfirmDTO request);

    String kakaoAuth(String code);

    String naverAuth(String code, String state);


}
