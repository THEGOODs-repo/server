package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.order.OrderConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public Orders create(OrderRequestDTO.OrderAddDto request, Member member) {
        // item 및 itemOption의 재고와 주문 수량 비교
        request.getOrderItemDtoList().forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            Integer stock;

            if (item.getPrice() == null) { // 옵션이 있는 상품인 경우
                ItemOption itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId()).get();
                stock = itemOption.getStock();
            } else { // 단일 상품인 경우
                stock = item.getStock();
            }

            if (orderItemDto.getAmount() > stock) { // 재고보다 주문 수량이 많은 경우
                throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
            }
        });

        // Orders 엔티티 생성 및 연관관계 매핑
        Orders newOrders = OrderConverter.toOrders(request);
        newOrders.setMember(member);
        Orders savedOrders = orderRepository.save(newOrders);

        // orderDetail 엔티티 생성 및 연관관계 매핑, 상품 재고 및 판매수 업데이트
        request.getOrderItemDtoList().forEach(orderItemDto -> {

            Long price;
            ItemOption itemOption = null;

            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            // 해당 상품의 옵션 유무 판별
            boolean haveOption = (item.getPrice() == null);

            if (haveOption) { // 상품 옵션이 존재하는 경우
                itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId()).get();
                price = itemOption.getPrice();
            } else { // 단일 상품인 경우
                price = item.getPrice();
            }
            // 엔티티 생성
            OrderDetail orderDetail = OrderConverter.toOrderDetail(orderItemDto, price);

            // 양방향 연관관계 매핑
            orderDetail.setOrders(savedOrders);
            orderDetail.setItem(item);
            if (haveOption) { // 상품 옵션이 존재하는 경우
                orderDetail.setItemOption(itemOption);
            }

            // Item, ItemOption 엔티티의 재고 및 판매수 업데이트
            Integer amount = orderItemDto.getAmount();
            item.updateSales(amount);

            if (haveOption) {
                itemOption.updateStock(-amount);
            } else {
                item.updateStock(-amount);
            }
        });

        return savedOrders;
    }
}


