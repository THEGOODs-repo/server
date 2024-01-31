package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.repository.order.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    Integer pageSize = 10;

    @Override
    public Page<OrderItem> getOrderItemList(Member member, OrderStatus orderStatus, Integer pageIdx) {

        Page<OrderItem> orderItemList;

        if (orderStatus == null) { // orderStatus 값이 없으면 전체 상태 조회 (필터링X)
            orderItemList = orderItemRepository.findAllByOrdersIn(member.getOrdersList(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
        } else { // orderStatus 값에 맞는 내역만 필터링
            orderItemList = orderItemRepository.findAllByStatusAndOrdersIn(orderStatus, member.getOrdersList(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
        }

        return orderItemList;
    }

    @Override
    public OrderItem getOrderItem(Member member, Long orderItemId) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_NOT_FOUND));

        if (!orderItem.getOrders().getMember().equals(member) && !orderItem.getItem().getMember().equals(member)) { // 해당 orderItem의 구매자 && 판매자가 아닌 경ㄷ
            log.info("orderItem.getOrders.getMember(): {}", orderItem.getOrders().getMember().getNickname());
            log.info("member parameter: {}", member.getNickname());

            throw new OrderHandler(ErrorStatus.NOT_ORDER_OWNER);
        }

        return orderItem;
    }

    @Override
    public boolean isExistOrderItem(Long id) {
        return orderItemRepository.existsById(id);
    }


}
