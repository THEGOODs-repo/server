package com.umc.TheGoods.web.dto.member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.enums.MemberStatus;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.redis.domain.RefreshToken;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        Long memberId;
        String nickname;
        String email;
        LocalDateTime createdAt;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        String accessToken;
        RefreshToken refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogoutResultDTO {
        Long memberId;
        MemberStatus memberStatus;
    }




    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthSendResultDTO {
        String phone;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthConfirmResultDTO {
        Boolean checkPhone;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailDuplicateConfirmResultDTO {
        Boolean checkEmail;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameDuplicateConfirmResultDTO {
        Boolean checkNickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneAuthConfirmFindEmailResultDTO {
        String email;
        String url;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAuthSendResultDTO {
        String email;


    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAuthConfirmResultDTO {
        Boolean checkEmail;
        String jwt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordUpdateResultDTO {
        boolean updatePassword;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialLoginResultDTO {
        String result;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialJoinResultDTO {
        String phone;
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileModifyResultDTO {
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileResultDTO {
        Long id;
        String name;
        String url;
        String phone;
        List<MemberResponseDTO.AddressDTO> addressList;
        List<MemberResponseDTO.AccountDTO> accountList;
        Long following;
        Long dibs;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleUpdateResultDTO {
        MemberRole role;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNameUpdateResultDTO {
        String name;
        String phone;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressResultDTO {
        String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDTO {

        Long id;
        String addressName;
        String addressSpec;
        String deliveryMemo;
        String zipcode;
        Boolean defaultCheck;
        String recipientPhone;
        String recipientName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountResultDTO {
        String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDTO {

        Long id;
        String owner;
        String bankName;
        String accountNum;
        Boolean defaultCheck;
    }



    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewTokenDTO{
        private String accessToken;
        private String refreshToken;
    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPageOrderItemDTO{
        private Long id;
        private String imageUrl;
        private Long price;
        private OrderStatus orderStatus;
        private String name;
        private LocalDateTime time;
        private List<String> option;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageOrderItemListDTO {
        List<MemberResponseDTO.MyPageOrderItemDTO> itemList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagDTO{
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO{
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomInfoDTO{
        List<TagDTO> tagList;
        List<CategoryDTO> categoryList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeclarationDTO{

        Long declarationId;
        //접수 항목
        String receipt;
        //판매 항목
        String salePost;
        //신고 이유
        String reason;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeclareResponseDTO{
        List<DeclarationDTO> declareDTOList;
    }




}
