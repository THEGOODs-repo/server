package com.umc.TheGoods.web.controller;


import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.Item.ItemConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.ItemService.ItemCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.web.dto.ItemRequestDTO;
import com.umc.TheGoods.web.dto.ItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class ItemRestController {

    private final ItemCommandService itemCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/seller/item")
    public ApiResponse<ItemResponseDTO.UploadItemResultDTO> upload(@RequestBody @Valid ItemRequestDTO.UploadItemDTO request,
                                                                   @RequestParam Long memberId,
                                                                   Authentication authentication){

        //MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Item item = itemCommandService.uploadItem(member,request);
        return ApiResponse.onSuccess(ItemConverter.toUploadItemResultDTO(item));
    }
}
