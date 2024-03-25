package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.domain.member.Member;

public interface PostCommandService {

    void follow(Long followingId, Member follower);

    void deleteFollow(Long followingId, Long followerId);

}
