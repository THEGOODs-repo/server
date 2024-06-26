package com.umc.TheGoods.converter.review;

import com.umc.TheGoods.domain.enums.ReviewStatus;
import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.web.dto.review.ReviewResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static Review toReview(OrderItem orderItem, String text, Integer score) {
        return Review.builder()
                .text(text)
                .score(score)
                .orderItem(orderItem)
                .status(ReviewStatus.SHOW)
                .build();
    }

    public static ReviewResponseDTO.reviewPostDTO toReviewPostDTO(Review review) {
        return ReviewResponseDTO.reviewPostDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .itemName(review.getItem().getName())
                .score(review.getScore())
                .text(review.getText())
                .optionStringList(ReviewConverter.toOptionStringList(review.getOrderItem()))
                .build();
    }

    private static List<String> toOptionStringList(OrderItem orderItem) {
        return orderItem.getOrderDetailList().stream().map(orderDetail -> {
            return orderDetail.getItemOption().getName();
        }).collect(Collectors.toList());
    }
}
