package com.umc.TheGoods.converter.item;

import com.umc.TheGoods.domain.enums.ItemStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.types.DeliveryType;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import com.umc.TheGoods.web.dto.item.ItemResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemConverter {

    public static ItemResponseDTO.UploadItemResultDTO toUploadItemResultDTO(Item item) {
        return ItemResponseDTO.UploadItemResultDTO.builder()
                .itemId(item.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Item toItem(ItemRequestDTO.UploadItemDTO request) {

        DeliveryType deliveryType = null;

        switch (request.getDeliveryType()) {
            case 1:
                deliveryType = DeliveryType.PO;
                break;
            case 2:
                deliveryType = DeliveryType.CJ;
                break;
            case 3:
                deliveryType = DeliveryType.LOTTE;
                break;
            case 4:
                deliveryType = DeliveryType.LOGEN;
                break;
            case 5:
                deliveryType = DeliveryType.HANJIN;
                break;
            case 6:
                deliveryType = DeliveryType.GS25;
                break;
            case 7:
                deliveryType = DeliveryType.CU;
                break;
            case 8:
                deliveryType = DeliveryType.ETC;
                break;
        }

        return Item.builder()
                .name(request.getName())
                .deliveryType(deliveryType)
                .description(request.getDescription())
                .deliveryFee(request.getDeliveryFee())
                .deliveryDate(request.getDeliveryDate())
                .isLimitless(request.getIsLimitless())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dibsCount(0L)
                .viewCount(0L)
                .salesCount(0L)
                .status(ItemStatus.ONSALE)
                .price(request.getPrice())
                .stock(request.getStock())
                .itemTagList(new ArrayList<>())
                .itemImgList(new ArrayList<>())
                .build();
    }
}
