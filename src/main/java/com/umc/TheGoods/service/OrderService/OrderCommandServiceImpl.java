package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.order.OrderConverter;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.OrderItem;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.order.OrderItemRepository;
import com.umc.TheGoods.repository.order.OrderRepository;
import com.umc.TheGoods.web.dto.order.OrderRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public Orders create(OrderRequestDTO.OrderAddDTO request, Member member) {
        // item 및 itemOption의 재고와 주문 수량 비교
        request.getOrderItemDTOList().forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            orderItemDto.getOrderDetailDTOList().forEach(orderDetailDTO -> {
                if (orderDetailDTO.getItemOptionId() != null) { // 옵션이 있는 상품인 경우
                    ItemOption itemOption = itemOptionRepository.findById(orderDetailDTO.getItemOptionId()).get();
                    if (orderDetailDTO.getAmount() > itemOption.getStock()) { // 재고보다 주문 수량이 많은 경우
                        throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                    }
                } else { // 단일 상품인 경우
                    if (orderDetailDTO.getAmount() > item.getStock()) {
                        throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                    }
                }
            });
        });

        // Orders 엔티티 생성 및 연관관계 매핑
        Orders newOrders = OrderConverter.toOrders(request);
        newOrders.setMember(member);
        Orders orders = orderRepository.save(newOrders);

        // OrderItem, OrderDetail 엔티티 생성, 연관관계 매핑, 판매 재고 업데이트
        request.getOrderItemDTOList().forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            // OrderItem 엔티티 생성 및 연관관계 매핑
            OrderItem newOrderItem = OrderConverter.toOrderItem(request, item.getDeliveryFee());
            newOrderItem.setItem(item);
            newOrderItem.setOrders(orders);
            OrderItem orderItem = orderItemRepository.save(newOrderItem);

            orderItemDto.getOrderDetailDTOList().forEach(orderDetailDTO -> {
                if (orderDetailDTO.getItemOptionId() != null) { // 옵션이 있는 상품인 경우
                    ItemOption itemOption = itemOptionRepository.findById(orderDetailDTO.getItemOptionId()).get();

                    // OrderDetail 엔티티 생성
                    OrderDetail orderDetail = OrderConverter.toOrderDetail(orderDetailDTO, itemOption.getPrice());
                    orderDetail.setOrderItem(orderItem);
                    orderDetail.setItemOption(itemOption);

                    // item, itemOption의 판매량, 재고 업데이트
                    item.updateSales(orderDetail.getAmount());
                    itemOption.updateStock(-orderDetail.getAmount());

                    // OrderItem 주문 상품 합산 금액 업데이트
                    orderItem.updateTotalPrice(orderDetail.getOrderPrice());
                } else {
                    OrderDetail orderDetail = OrderConverter.toOrderDetail(orderDetailDTO, item.getPrice());
                    orderDetail.setOrderItem(orderItem);

                    // item의 판매량, 재고 업데이트
                    item.updateSales(orderDetail.getAmount());
                    item.updateStock(-orderDetail.getAmount());

                    // OrderItem 주문 상품 합산 금액 업데이트
                    orderItem.updateTotalPrice(orderDetail.getOrderPrice());
                }
            });
            orderItem.updateTotalPrice(Long.valueOf(item.getDeliveryFee()));
        });

        return orders;
    }

    @Override
    public OrderItem updateStatusToConfirm(Long orderItemId, Member member) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_NOT_FOUND));

        // 해당 orderItem을 수정할 권한이 있는지 검증
        if (!orderItem.getOrders().getMember().equals(member)) {
            throw new OrderHandler(ErrorStatus.NOT_ORDER_OWNER);
        }

        // 해당 orderItem의 상태가 DEL_COMP인지 검증
        if (orderItem.getStatus() != OrderStatus.DEL_COMP) {
            throw new OrderHandler(ErrorStatus.ORDER_ITEM_UPDATE_FAIL);
        }

        return orderItem.updateStatus(OrderStatus.CONFIRM);
    }
}


