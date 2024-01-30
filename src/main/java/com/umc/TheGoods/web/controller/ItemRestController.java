package com.umc.TheGoods.web.controller;


import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.item.ItemConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.ItemService.ItemCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Item", description = "상품 관련 API")
@RequestMapping("/api")
public class ItemRestController {

    private final ItemCommandService itemCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/seller/item")
    @Operation(summary = "상품 등록 API", description = "상품 등록을 위한 API이며, requset 파라미터로 입력 값을 받는다. " +
            "배송 방법(delivery_type) (1~8) : PO, CJ, LOTTE, LOGEN, HANJIN, GS25, CU, ETC")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ItemResponseDTO.UploadItemResultDTO> upload(@RequestBody @Valid ItemRequestDTO.UploadItemDTO request,
                                                                   Authentication authentication) {

        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Item item = itemCommandService.uploadItem(member, request);
        return ApiResponse.onSuccess(ItemConverter.toUploadItemResultDTO(item));
    }
}
