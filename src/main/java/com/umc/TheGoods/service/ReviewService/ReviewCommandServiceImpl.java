package com.umc.TheGoods.service.ReviewService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.apiPayload.exception.handler.ReviewHandler;
import com.umc.TheGoods.converter.review.ReviewConverter;
import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.repository.order.OrderItemRepository;
import com.umc.TheGoods.repository.review.ReviewRepository;
import com.umc.TheGoods.web.dto.review.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 등록
     *
     * @param request
     * @param member
     * @return
     */
    @Override
    public Review create(ReviewRequestDTO.addReviewDTO request, Member member) {
        // 해당 orderItem이 해당 회원의 것이 맞는지 검증
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId()).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_NOT_FOUND));
        if (!orderItem.getOrders().getMember().equals(member)) {
            throw new ReviewHandler(ErrorStatus.NOT_ORDER_OWNER);
        }

        // 이미 리뷰를 등록했던 orderItem인지 검증
        boolean isExists = reviewRepository.existsByOrderItem(orderItem);
        if (isExists) {
            throw new ReviewHandler(ErrorStatus.REVIEW_ALREADY_EXISTS);
        }

        // review 엔티티 생성 및 연관관계 매핑
        Review review = ReviewConverter.toReview(orderItem, request.getText(), request.getScore());
        review.setMember(member);
        review.setItem(orderItem.getItem());

        return reviewRepository.save(review);
    }
}
