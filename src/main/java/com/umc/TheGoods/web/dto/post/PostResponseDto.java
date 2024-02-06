package com.umc.TheGoods.web.dto.post;

import com.umc.TheGoods.domain.images.PostImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

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
    public static class PostListViewDto {
        Integer offset;
        Integer size;
        List<PostViewDto> posts;
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
