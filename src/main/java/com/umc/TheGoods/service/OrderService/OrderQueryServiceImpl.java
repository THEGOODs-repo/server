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
import java.util.Optional;

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

            Integer stock;

            if (Optional.ofNullable(orderItemDto.getItemOptionId()).isPresent()) { // itemOptionId가 있는 경우
                // itemOption 존재 여부 검증
                ItemOption itemOption = itemOptionRepository.findById(orderItemDto.getItemOptionId().get()).orElseThrow(() -> new ItemHandler(ErrorStatus._ITEMOPTION_NOT_FOUND));

                // itemOption 정상 요청 검증: 해당 itemOptionId가 그 item의 것인지 검증
                if (!itemOption.getItem().getId().equals(item.getId())) { // 요청한 itemOption에 해당하는 item의 id가 요청한 item id와 다른 경우
                    throw new ItemHandler(ErrorStatus._ITEMOPTION_NOT_MATCH);
                }

                // itemOption 재고 검색
                stock = itemOption.getStock();

            } else { // itemOptionId가 없는 경우
                // 해당 item이 단일 상품인지 검증: 단일 상품이 아닌데 옵션을 선택하지 않은 경우 에러 발생
                if (item.getPrice() == null) { // 단일 상품이 아님
                    throw new OrderHandler(ErrorStatus._NULL_ITEMOPTION_ERROR);
                }

                // item 재고 검색
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
