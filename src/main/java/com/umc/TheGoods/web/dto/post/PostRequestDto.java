package com.umc.TheGoods.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentDTO {
        private Long parentId;
        private String comment;
    }
}
