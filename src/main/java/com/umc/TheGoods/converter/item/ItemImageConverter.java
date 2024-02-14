package com.umc.TheGoods.converter.item;

import com.umc.TheGoods.domain.images.ItemImg;

public class ItemImageConverter {

    public static ItemImg toItemImg(String imgUrl, Boolean isThumbnail) {
        return ItemImg.builder()
                .thumbnail(isThumbnail)
                .url(imgUrl)
                .build();
    }
}
