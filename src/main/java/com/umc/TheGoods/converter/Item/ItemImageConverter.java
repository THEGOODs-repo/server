package com.umc.TheGoods.converter.Item;

import com.umc.TheGoods.domain.images.ItemImg;
import com.umc.TheGoods.web.dto.ItemRequestDTO;

public class ItemImageConverter {

    public static ItemImg toItemImg(ItemRequestDTO.itemImgDTO request){
        return ItemImg.builder()
                .thumbnail(request.getIsThumbnail())
                .url(request.getImgUrl())
                .build();
    }
}
