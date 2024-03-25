package com.umc.TheGoods.converter.post;

import com.umc.TheGoods.domain.member.Follow;
import com.umc.TheGoods.domain.member.Member;

public class PostConverter {
    public static Follow toFollow(Member follower, Member following){

        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
