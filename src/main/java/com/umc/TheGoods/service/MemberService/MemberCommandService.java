package com.umc.TheGoods.service.MemberService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.redis.domain.RefreshToken;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import org.springframework.web.multipart.MultipartFile;


public interface MemberCommandService {

    boolean existCategoryById(Long categoryId);

    boolean existMemberById(Long memberId);

    Member join(MemberRequestDTO.JoinDTO request);

    MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDTO request);
    void logout(String accessToken, Member member);
    String regenerateAccessToken(RefreshToken refreshToken);

    Auth sendPhoneAuth(String phone) throws JsonProcessingException;

    Boolean confirmPhoneAuth(MemberRequestDTO.PhoneAuthConfirmDTO request);

    Boolean confirmEmailDuplicate(MemberRequestDTO.EmailDuplicateConfirmDTO request);

    Boolean confirmNicknameDuplicate(MemberRequestDTO.NicknameDuplicateConfirmDTO request);

    String confirmPhoneAuthFindEmail(MemberRequestDTO.PhoneAuthConfirmFindEmailDTO request);

    Auth sendEmailAuth(String email);

    Boolean confirmEmailAuth(MemberRequestDTO.EmailAuthConfirmDTO request);

    String emailAuthCreateJWT(MemberRequestDTO.EmailAuthConfirmDTO request);

    Boolean updatePassword(MemberRequestDTO.PasswordUpdateDTO request, Member member);

    String kakaoAuth(String code);

    String naverAuth(String code, String state);

    Member profileModify(MultipartFile profile, String nickname, String introduce, Member member);


    void updatePhoneName(MemberRequestDTO.PhoneNameUpdateDTO request, Member member);

    Address postAddress(MemberRequestDTO.AddressDTO request,Member member);

    Account postAccount(MemberRequestDTO.AccountDTO request, Member member);

    void updateAddress(MemberRequestDTO.AddressDTO request,Member member, Long addressId);

    void updateAccount(MemberRequestDTO.AccountDTO request, Member member,Long accountId);

    void deleteAccount(Member member, Long accountId);

    void deleteMember(MemberRequestDTO.WithdrawReasonDTO request, Long memberId);

    void updateNotification(Member member, Integer type);

    void deleteAddress(Member member, Long addressId);


    void postDeclare(Member member, MemberRequestDTO.DeclareDTO request);

    void deleteDeclare(Long declarationId, Member member);

    void postContact(Long memberId, MemberRequestDTO.ContactDTO request);

}
