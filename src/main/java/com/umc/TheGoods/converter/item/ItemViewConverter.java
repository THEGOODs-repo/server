package com.umc.TheGoods.converter.item;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.member.Member;

public class ItemViewConverter {

    public static ItemView toItemView(Member member, Item item) {
        return ItemView.builder()
                .item(item)
                .member(member)
                .build();
    }
}
