package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.web.dto.post.PostResponseDto;

import java.util.List;

public interface PostQueryService {
    List<PostResponseDto.PostViewDto> getPostsOrderByLike();
}
