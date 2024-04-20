package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.apiPayload.exception.handler.PostHandler;
import com.umc.TheGoods.converter.post.PostConverter;
import com.umc.TheGoods.domain.community.Post;
import com.umc.TheGoods.domain.images.PostImg;
import com.umc.TheGoods.domain.member.Follow;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.member.MemberRepository;
import com.umc.TheGoods.repository.post.FollowRepository;
import com.umc.TheGoods.repository.post.PostImgRepository;
import com.umc.TheGoods.repository.post.PostRepository;
import com.umc.TheGoods.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final UtilService utilService;
    private final PostImgRepository postImgRepository;

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

    @Override
    public void registerPost(Member member, String content, List<MultipartFile> multipartFileList) {
        Post post = PostConverter.toPost(member,content);

        if (multipartFileList != null) {
            List<PostImg> postImgList = multipartFileList.stream().map(multipartFile -> {
                String postImgUrl = utilService.uploadS3Img("post", multipartFile);

                PostImg postImg = PostConverter.toPostImg(postImgUrl, post);
                return postImg;
            }).collect(Collectors.toList());

            postImgRepository.saveAll(postImgList);
        }

        postRepository.save(post);
    }

    @Override
    public void updatePost(Member member,Long postId, String content, List<MultipartFile> multipartFileList) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        post.updatePost(content);

        List<PostImg> postImgList = postImgRepository.findByPostId(postId);
        postImgRepository.deleteAll(postImgList);

        if (multipartFileList != null) {
            List<PostImg> newPostImgList = multipartFileList.stream().map(multipartFile -> {
                String postImgUrl = utilService.uploadS3Img("post", multipartFile);

                PostImg postImg = PostConverter.toPostImg(postImgUrl, post);
                return postImg;
            }).collect(Collectors.toList());

            postImgRepository.saveAll(newPostImgList);
        }



    }
}
