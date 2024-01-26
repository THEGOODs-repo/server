package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.converter.orders.OrderConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.order.OrderRepository;
import com.umc.TheGoods.web.dto.order.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderQueryService orderQueryService;

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public Orders create(OrderRequest.OrderAddDto request, Member member) {
        // request의 OrderItemDtoList에 담긴 상품 정보 및 재고 검증
        // 검증 실패 시 exception 발생
        orderQueryService.isOrderAvailable(request.getOrderItemDtoList());

        // Orders 엔티티 생성 및 연관관계 매핑
        Orders newOrders = OrderConverter.toOrders(request);
        newOrders.setMember(member);

        // orderDetail 엔티티 생성 및 연관관계 매핑, 상품 재고 및 판매수 업데이트
        request.getOrderItemDtoList().forEach(orderItemDto -> {

            Long price;
            ItemOption itemOption = null;

            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            // 해당 상품의 옵션 유무 판별
            boolean haveOption = (item.getPrice() == null);

            if (haveOption) { // 상품 옵션이 존재하는 경우
                itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId().get()).get();
                price = itemOption.getPrice();
            } else { // 단일 상품인 경우
                price = item.getPrice();
            }
            // 엔티티 생성
            OrderDetail orderDetail = OrderConverter.toOrderDetail(orderItemDto, price);

            // 양방향 연관관계 매핑
            orderDetail.setOrders(newOrders);
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

        return orderRepository.save(newOrders);
    }
}


