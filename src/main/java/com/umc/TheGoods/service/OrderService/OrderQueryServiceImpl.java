package com.umc.TheGoods.service.OrderService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.web.dto.order.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public Boolean isOrderAvailable(List<OrderRequest.OrderItemDto> orderItemDtoList) {
        orderItemDtoList.forEach(orderItemDto -> {
            // item 존재 여부 검증
            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow(() -> new ItemHandler(ErrorStatus._ITEM_NOT_FOUND));

            Integer stock = 0;

            if (orderItemDto.getItemOptionId() != null) { // 상품의 옵션이 존재하는 경우
                // itemOption 존재 여부 검증
                ItemOption itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId().get()).orElseThrow(() -> new ItemHandler(ErrorStatus._ITEMOPTION_NOT_FOUND));

                // itemOption 재고 검색
                stock = itemOption.getStock();

            } else { // 단일 상품인 경우
                stock = item.getStock();
            }

            // 주문 수량이 상품 재고를 초과하는 경우
            if (orderItemDto.getAmount() > stock) {
                throw new OrderHandler(ErrorStatus._LACK_OF_STOCK);
            }


        });
        return true;
    }
}
