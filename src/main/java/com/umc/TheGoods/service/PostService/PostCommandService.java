package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.post.PostRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCommandService {

    void follow(Long followingId, Member follower);

    void deleteFollow(Long followingId, Long followerId);

    void registerPost(Member member, String content, List<MultipartFile> multipartFileList);

    void updatePost(Member member, Long postId, String content, List<MultipartFile> multipartFileList);

    void deletePost(Member member, Long postId);

    void likePost(Member member, Long postId);


    void uploadComment(Member member, Long postId, PostRequestDto.CommentDTO request);

    void updateComment(Member member, Long postId, Long commentId, PostRequestDto.UpdateCommentDTO request);

    void likeComment(Member member, Long commentId);


}
