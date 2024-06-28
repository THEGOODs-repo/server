package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.converter.review.ReviewConverter;
import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.ReviewService.ReviewCommandService;
import com.umc.TheGoods.web.dto.review.ReviewRequestDTO;
import com.umc.TheGoods.web.dto.review.ReviewResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Review", description = "리뷰 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final MemberQueryService memberQueryService;
    private final ReviewCommandService reviewCommandService;

    @PostMapping
    public ApiResponse<ReviewResponseDTO.reviewPostDTO> addReview(@RequestBody @Valid ReviewRequestDTO.addReviewDTO request,
                                                                  Authentication authentication) {
        // 비회원인 경우 처리 불가
        if (authentication == null) {
            throw new MemberHandler(ErrorStatus._UNAUTHORIZED);
        }

        // request에서 member id 추출해 Member 엔티티 찾기
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 리뷰 등록
        Review review = reviewCommandService.create(request, member);

        return ApiResponse.onSuccess(ReviewConverter.toReviewPostDTO(review));
    }

}
