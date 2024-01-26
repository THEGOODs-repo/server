package com.umc.TheGoods.converter;

import com.umc.TheGoods.domain.enums.ItemStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.types.DeliveryType;
import com.umc.TheGoods.web.dto.ItemRequestDTO;

import java.util.ArrayList;

public class ItemConverter {

    public static Item toItem(ItemRequestDTO.UploadItemDTO request){

        DeliveryType deliveryType = null;

        switch (request.getDeliveryType()){
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
                .delivery_date(request.getDeliveryDate())
                .isLimitless(request.getIsLimitless())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dibsCount(0L)
                .viewCount(0L)
                .salesCount(0L)
                .status(ItemStatus.ONSALE)
                .price(request.getPrice())
                .itemImgList(new ArrayList<>())
                .itemImgList(new ArrayList<>())
                .build();
    }
}
