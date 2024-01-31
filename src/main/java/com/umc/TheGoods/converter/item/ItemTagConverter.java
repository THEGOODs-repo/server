package com.umc.TheGoods.converter.item;

import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;

import java.util.List;
import java.util.stream.Collectors;

public class ItemTagConverter {

    public static List<ItemTag> toItemTagList(List<Tag> tagList) {

        return tagList.stream()
                .map(tag ->
                        ItemTag.builder()
                                .tag(tag)
                                .build()
                ).collect(Collectors.toList());
    }
}
