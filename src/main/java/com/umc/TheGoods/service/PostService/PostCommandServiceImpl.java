package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.apiPayload.exception.handler.PostHandler;
import com.umc.TheGoods.converter.post.PostConverter;
import com.umc.TheGoods.domain.member.Follow;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.member.MemberRepository;
import com.umc.TheGoods.repository.post.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Override
    public void follow(Long followingId, Member follower) {

        if(followingId.equals(follower.getId())){
            throw new PostHandler(ErrorStatus.POST_SELF_FOLLOW);
        }
        Optional<Follow> check = followRepository.findByFollowingIdAndFollowerId(followingId,follower.getId());
        if(!check.isEmpty()){
            throw new PostHandler(ErrorStatus.POST_ALREADY_FOLLOW);
        }

        Member following = memberRepository.findById(followingId).orElseThrow(()->new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Follow follow = PostConverter.toFollow(follower,following);
        followRepository.save(follow);
    }

    @Override
    public void deleteFollow(Long followingId, Long followerId) {

        Follow follow = followRepository.findByFollowingIdAndFollowerId(followingId,followerId).orElseThrow(() -> new PostHandler(ErrorStatus.POST_FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }
}
