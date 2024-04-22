package com.umc.TheGoods.converter.post;


import com.umc.TheGoods.domain.community.Post;
import com.umc.TheGoods.domain.images.PostImg;
import com.umc.TheGoods.domain.mapping.post.PostLike;
import com.umc.TheGoods.domain.member.Follow;
import com.umc.TheGoods.domain.member.Member;

public class PostConverter {
    public static Follow toFollow(Member follower, Member following){

        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }

    public static Post toPost(Member member, String content){

        return Post.builder()
                .content(content)
                .member(member)
                .build();
    }

    public static PostImg toPostImg(String url, Post post){

        return PostImg.builder()
                .post(post)
                .url(url)
                .build();
    }

    public static PostLike toPostLike(Member member, Post post){

        return PostLike.builder()
                .member(member)
                .post(post)
                .build();
    }
}
