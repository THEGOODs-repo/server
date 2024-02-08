package com.umc.TheGoods.web.controller;


import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.item.ItemConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.ItemService.ItemCommandService;
import com.umc.TheGoods.service.ItemService.ItemQueryService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.validation.annotation.CheckPage;
import com.umc.TheGoods.validation.annotation.ExistItem;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Item", description = "상품 관련 API")
@RequestMapping("/api")
public class ItemRestController {

    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;
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

    @GetMapping("/seller/item/{itemId}")
    @Operation(summary = "상품 조회 API", description = "상품 조회를 위한 API이며, path variable로 입력 값을 받는다. " +
            "itemId : 조회할 상품의 id")
    @Parameters(value = {
            @Parameter(name = "itemId", description = "조회할 상품의 id 입니다.")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ItemResponseDTO.ItemContentDTO> getPostContent(@ExistItem @PathVariable(name = "itemId") Long itemId, Authentication authentication) {
        Member member;

        if (authentication == null) {
            member = memberQueryService.findMemberByNickname("no_login_user").orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_VIEW_ERROR));
        } else {
            MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
            member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        }

        Item itemContent = itemCommandService.getItemContent(itemId, member);
        return ApiResponse.onSuccess(ItemConverter.getItemContentDTO(itemContent));
    }

    @PutMapping("/seller/item/{itemId}")
    @Operation(summary = "상품 수정 API", description = "상품 수정을 위한 API이며, path variable로 입력 값을 받습니다. " +
            "itemId : 수정할 상품의 id")
    @Parameters(value = {
            @Parameter(name = "itemId", description = "조회할 상품의 id 입니다.")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ItemResponseDTO.UpdateItemResultDTO> updateItem(@RequestBody @Valid ItemRequestDTO.UpdateItemDTO request,
                                                                       @ExistItem @PathVariable(name = "itemId") Long itemId, Authentication authentication) {
        Member member;

        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Item item = itemCommandService.updateItem(itemId, member, request);
        return ApiResponse.onSuccess(ItemConverter.toUpdateItemResultDTO(item));
    }

    @GetMapping("/seller/item/")
    @Operation(summary = "나의 판매 상품 조회 API", description = "상품 수정을 위한 API이며, request parameter로 입력 값을 받습니다. " +
            "page : 상품 조회 페이지 번호")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ItemResponseDTO.ItemPreviewListDTO> getMyItemList(@CheckPage @RequestParam(name = "page") Integer page,
                                                                         Authentication authentication) {
        if (authentication == null) {
            throw new MemberHandler(ErrorStatus._UNAUTHORIZED);
        }

        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Page<Item> itemList = itemQueryService.getMyItemList(member, page - 1);
        return ApiResponse.onSuccess(ItemConverter.itemPreviewListDTO(itemList));
    }

    @GetMapping("/search/item/")
    @Operation(summary = "판매 상품 검색 API", description = "상품 검색을 위한 API이며, request parameter로 입력 값을 받습니다. " +
            "page : 상품 조회 페이지 번호")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ItemResponseDTO.ItemPreviewListDTO> searchItemList(@CheckPage @RequestParam(name = "page") Integer page,
                                                                          @RequestParam(name = "itemName", required = false) String itemName,
                                                                          @RequestParam(name = "category", required = false) String categoryName,
                                                                          @RequestParam(name = "sellerName", required = false) String sellerName,
                                                                          @RequestParam(name = "tagNames", required = false) List<String> tagName,
                                                                          Authentication authentication) {
        Member member;

        if (authentication == null) {
            member = memberQueryService.findMemberByNickname("no_login_user").orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_VIEW_ERROR));
        } else {
            MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
            member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        }

        Page<Item> itemList = itemQueryService.searchItem(member, itemName, categoryName, sellerName, tagName, page - 1);

        return ApiResponse.onSuccess(ItemConverter.itemPreviewListDTO(itemList));
    }
}
