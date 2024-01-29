package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.repository.MemberRepository;
import com.umc.TheGoods.repository.order.OrderDetailRepository;
import com.umc.TheGoods.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    Integer pageSize = 10;

    @Override
    public Page<OrderDetail> getOrderDetailList(Long memberId, OrderStatus orderStatus, Integer pageIdx) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Page<OrderDetail> orderDetailList;

        if (orderStatus == null) { // orderStatus 값이 없으면 전체 상태 조회 (필터링X)
            orderDetailList = orderDetailRepository.findAllByOrdersIn(member.getOrdersList(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
        } else { // orderStatus 값에 맞는 내역만 필터링
            orderDetailList = orderDetailRepository.findAllByStatusAndOrdersIn(orderStatus, member.getOrdersList(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
        }

        return orderDetailList;
    }
}
