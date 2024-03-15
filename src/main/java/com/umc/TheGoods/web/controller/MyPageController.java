package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.code.status.SuccessStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.member.MemberConverter;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.mypage.Account;
import com.umc.TheGoods.domain.mypage.Address;
import com.umc.TheGoods.service.MemberService.MemberCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.OrderService.OrderQueryService;
import com.umc.TheGoods.validation.annotation.CheckPage;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "MyPage", description = "MyPage 관련 API")
@RequestMapping("/api/members/mypage")
public class MyPageController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final OrderQueryService orderQueryService;
    @PutMapping(value = "/profile/modify", consumes = "multipart/form-data")
    @Operation(summary = "마이페이지 프로필 수정(닉네임, 프로필 사진, 소개) api", description = "request : 프로필 이미지, 닉네임, 자기소개 ")
    public ApiResponse<MemberResponseDTO.ProfileModifyResultDTO> profileModify(@RequestParam("profile") MultipartFile profile,
                                                                               @RequestParam("nickname") String nickname,
                                                                               @RequestParam("introduce") String introduce,
                                                                               Authentication authentication) {
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));


        Member modifyMember = memberCommandService.profileModify(profile, nickname, introduce, member);

        return ApiResponse.onSuccess(MemberConverter.toProfileModify(modifyMember));
    }


    @PutMapping(value = "/role/update")
    @Operation(summary = "사용자 역할 전환 api", description = "BUYER은 SELLER로 SELLER는 BUYER로 역할 변경")
    public ApiResponse<MemberResponseDTO.RoleUpdateResultDTO> updateRole(Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Member update = memberCommandService.updateRole(member);



        return ApiResponse.onSuccess(MemberConverter.toUpdateRole(update));
    }


    @PostMapping(value = "/address")
    @Operation(summary = "회원 배송지 추가 api", description = "request: 우편번호, 배송지명, 배송지, 배송메모")
    public ApiResponse<MemberResponseDTO.AddressResultDTO> postAddress(@RequestBody MemberRequestDTO.AddressDTO request, Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Address address = memberCommandService.postAddress(request,member);

        return ApiResponse.onSuccess(MemberConverter.toPostAddressDTO(address.getAddressName()));
    }

    @PostMapping(value = "/account")
    @Operation(summary = "회원 계좌 추가 api", description = "request: 소유주 이름, 은행 이름, 계좌번호")
    public ApiResponse<MemberResponseDTO.AccountResultDTO> postAccount(@RequestBody MemberRequestDTO.AccountDTO request, Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Account account = memberCommandService.postAccount(request,member);

        return ApiResponse.onSuccess(MemberConverter.toPostAccountDTO(account.getOwner()));
    }

    @PutMapping(value = "/address/update/{addressId}")
    @Operation(summary = "회원 주소 수정 api", description = "request: 우편번호, 배송지명, 배송지, 배송메모")
    public ApiResponse<MemberResponseDTO.AddressResultDTO> updateAddress(@RequestBody MemberRequestDTO.AddressDTO request,
                                                                         @PathVariable (name = "addressId") Long addressId,
                                                                         Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        memberCommandService.updateAddress(request, addressId);

        return ApiResponse.onSuccess(null);
    }

    @PutMapping(value = "/account/update/{accountId}")
    @Operation(summary = "회원 계좌 수정 api", description = "request: 소유주 이름, 은행 이름, 계좌번호")
    public ApiResponse<MemberResponseDTO.AccountResultDTO> updateAccount(@RequestBody MemberRequestDTO.AccountDTO request,
                                                                         @PathVariable (name = "accountId") Long accountId,
                                                                         Authentication authentication) {

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        memberCommandService.updateAccount(request,accountId);

        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "회원 탈퇴 api", description = "request: 회원 탈퇴 사유 번호로 주시면 됩니다")
    public ApiResponse<?> deleteMember(@RequestBody MemberRequestDTO.WithdrawReasonDTO request,
                                       Authentication authentication){

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        memberCommandService.deleteMember(request, member);
        return ApiResponse.of(SuccessStatus.MEMBER_DELETE_SUCCESS, null);
    }

    /**
     * 한 페이지에 8개씩 상품정보 가져오기
     * OrderStatus 결제전, 결제 완료, 배송준비, 배송 시작, 배송완료, 주문 취소, 구매 확정, 반품 진행중, 반품 완료 주문 상태 별로 상품 조회 가능해야함
     * 주문시간, 주문상태(결제전, 결제완료), 상품 이름, 상품 옵션, 가격, 사진
     */
    @GetMapping(value = "/mypage/item")
    @Operation(summary = "mypage 구매관리 조회 api", description = "마이페이지에서 구매관리 페이지에서 상품정보를 가져오는 api입니다.\n" +
            "request parameter에 페이지 번호와 주문 상태를 넣어줘야합니다.")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요."),
            @Parameter(name = "orderStatus", description = "전체 조회: null, 결제전: PAY_PREV, 결제완료: PAY_COMP, 배송준비: DEL_PREP, 배송시작: DEL_START, 배송완료: DEL_COMP, 주문취소: CANCEL, 구매확정: CONFIRM, 반품 진행중: REFUND_ONGOING, 반품완료: REFUND_COMP")
    })
    public ApiResponse<MemberResponseDTO.MyPageOrderItemListDTO> getMyPageItemList(@CheckPage @RequestParam Integer page,
                                                                                   @RequestParam(required = false) OrderStatus orderStatus,
                                                                                   Authentication authentication){
        // jwt로 사용자 정보 찾기
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        //주문 상태에 따른 상품 데이터 Page로 가져오기


        return ApiResponse.onSuccess(orderQueryService.getMyPageOrderItemList(member,orderStatus,page));
    }

}
