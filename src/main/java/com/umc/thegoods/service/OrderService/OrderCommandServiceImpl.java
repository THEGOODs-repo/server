package com.umc.thegoods.service.OrderService;

import com.umc.thegoods.converter.orders.OrderConverter;
import com.umc.thegoods.domain.item.Item;
import com.umc.thegoods.domain.item.ItemOption;
import com.umc.thegoods.domain.member.Member;
import com.umc.thegoods.domain.order.OrderDetail;
import com.umc.thegoods.domain.order.Orders;
import com.umc.thegoods.repository.item.ItemOptionRepository;
import com.umc.thegoods.repository.item.ItemRepository;
import com.umc.thegoods.repository.order.OrderDetailRepository;
import com.umc.thegoods.repository.order.OrderRepository;
import com.umc.thegoods.web.dto.order.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderQueryService orderQueryService;

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public Orders create(OrderRequest.OrderAddDto request, Member member) {
        // request의 orderList에 담긴 상품 정보 및 재고 검증
        // 검증 실패 시 exception 발생
        orderQueryService.isOrderAvailable(request.getOrderItemDtoList());

        // Orders 엔티티 생성 및 저장
        Orders newOrders = OrderConverter.toOrders(request);
        newOrders.setMember(member);
        Orders orders = orderRepository.save(newOrders);

        // orderDetail 엔티티 생성 및 연관관계 매핑
        List<OrderDetail> orderDetailList = request.getOrderItemDtoList().stream().map(orderItemDto -> {

            Long price = 0L;
            Item item = itemRepository.findById(orderItemDto.getItemId()).get();

            // 해당 상품의 옵션 유무 판별
            Boolean haveOption = (item.getPrice() == null);

            ItemOption itemOption = null;

            if (haveOption) { // 상품 옵션이 존재하는 경우
                itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId().get()).get();
                price = itemOption.getPrice();
            } else { // 단일 상품인 경우
                price = itemRepository.findById(orderItemDto.getItemId()).get().getPrice();
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

            return orderDetail;
        }).collect(Collectors.toList());

        // OrderDetail 엔티티 저장
        orderDetailList.forEach(orderDetail -> orderDetailRepository.save(orderDetail));

        return orders;
    }
}
