package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.service.PostService.PostQueryService;
import com.umc.TheGoods.web.dto.post.PostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    final PostQueryService queryService;

    @GetMapping("/")
    @Operation(summary = "인기 사장님 피드 전체 조회 API", description = "포스트의 기본 디폴트 정렬로, 좋아요 순으로 내림차순 정렬합니다. \n\n")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<PostResponseDto.PostListViewDto> posts(@RequestParam("like") boolean like) {
        return ApiResponse.onSuccess((PostResponseDto.PostListViewDto) queryService.getPostsOrderByLike());
    }

    @GetMapping("/follow")
    public ApiResponse<PostResponseDto.PostListViewDto> following(@RequestParam("like") boolean like) {
        return null;
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostViewDto> post(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostStatusDto> registerPost(@PathVariable Long postId) {
        return null;
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostStatusDto> updatePost(@PathVariable Long postId) {
        return null;
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostStatusDto> deletePost(@PathVariable Long postId) {
        return null;
    }

}
