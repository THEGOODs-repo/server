package com.umc.TheGoods.service.ReviewService;

import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.review.ReviewRequestDTO;

public interface ReviewCommandService {

    Review create(ReviewRequestDTO.addReviewDTO request, Member member);
}
