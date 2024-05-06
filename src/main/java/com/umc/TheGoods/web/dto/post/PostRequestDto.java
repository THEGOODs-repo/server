package com.umc.TheGoods.web.dto.post;

<<<<<<< HEAD
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String contentImg;
    private Long createdAt;
    private Long updatedAt;
=======
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
>>>>>>> 14ad16a (:sparkles: feat: 포스트 댓글 api 구현)
}
