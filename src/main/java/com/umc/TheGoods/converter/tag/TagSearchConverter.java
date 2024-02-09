package com.umc.TheGoods.converter.tag;

import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.ViewSearch.TagSearch;
import com.umc.TheGoods.domain.member.Member;

import java.util.List;
import java.util.stream.Collectors;

public class TagSearchConverter {

    public static List<TagSearch> toTagSearchList(List<Tag> tagList, Member member) {

        return tagList.stream()
                .map(tag ->
                        TagSearch.builder()
                                .tag(tag)
                                .member(member)
                                .build()
                ).collect(Collectors.toList());
    }
}
