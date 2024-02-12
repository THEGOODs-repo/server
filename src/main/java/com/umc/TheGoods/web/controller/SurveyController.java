package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.survey.SurveyConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.SurveyService.SurveyCommandService;
import com.umc.TheGoods.web.dto.member.MemberDetail;
import com.umc.TheGoods.web.dto.survey.SurveyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Survey", description = "선호도 조사 관련 API")
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyCommandService surveyCommandService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/popular/seller")
    @Operation(summary = "해당 카테고리 인기 사장님 조회 API", description = "인기 사장님 정보 가져오기")
    public ApiResponse<List<SurveyResponseDTO.PopularSellerResultDTO>> getPopularSeller(Authentication authentication) {

        MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        Member member = memberQueryService.findMemberById(memberDetail.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Item> item = surveyCommandService.getPopularItems(member);


        List<Member> popular_member = surveyCommandService.getPopularSeller(item);
        return ApiResponse.onSuccess(SurveyConverter.toPopularSellerResultDTO(popular_member, item));
    }
}
