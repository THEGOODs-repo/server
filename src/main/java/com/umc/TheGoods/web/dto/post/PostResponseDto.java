package com.umc.TheGoods.web.dto.post;

import com.umc.TheGoods.domain.community.Post;
import com.umc.TheGoods.domain.images.PostImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String content;
    private final Long viewCount;
    private final Integer likesCount;
    private final Long memberId;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.likesCount = post.getLikesCount();
        this.memberId = post.getMember().getId();
    }

    @Getter
    public static class PostListViewDto {
        private final List<PostResponseDto> posts;

        public PostListViewDto(List<PostResponseDto> posts) {
            this.posts = posts;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostViewDto {
        Long id;
        String title;
        String content;
        LocalDateTime createdDate;
        LocalDateTime updatedDate;
        Integer likes;
        List<PostImg> images;
        Long writerId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostStatusDto {
        Long postId;
        String status;
    }
}
