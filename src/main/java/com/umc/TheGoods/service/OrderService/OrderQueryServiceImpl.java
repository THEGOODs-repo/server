package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
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
    public Page<OrderDetail> getOrderDetailList(Long memberId, Integer pageIdx) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Page<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrdersIn(member.getOrdersList(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));

        return orderDetailList;
    }
}
