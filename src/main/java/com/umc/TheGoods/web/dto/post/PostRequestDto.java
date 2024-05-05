package com.umc.TheGoods.web.dto.post;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String contentImg;
    private Long createdAt;
    private Long updatedAt;
}
