package com.umc.TheGoods.web.dto.post;

import com.umc.TheGoods.domain.community.Post;
import lombok.Getter;

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
}
