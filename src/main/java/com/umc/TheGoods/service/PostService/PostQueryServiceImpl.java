package com.umc.TheGoods.service.PostService;

import com.umc.TheGoods.repository.PostRepository;
import com.umc.TheGoods.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    @Override
    public List<PostResponseDto.PostViewDto> getPostsOrderByLike() {
        return null;
    }
}
