package com.umc.TheGoods.converter.item;

import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;

public class ItemOptionConverter {

    public static ItemOption toItemOption(ItemRequestDTO.itemOptionDTO request) {
        return ItemOption.builder()
                .name(request.getName())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
    }
}
