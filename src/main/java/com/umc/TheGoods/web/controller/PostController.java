package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.code.status.SuccessStatus;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.domain.member.Auth;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.service.PostService.PostCommandService;
import com.umc.TheGoods.service.PostService.PostQueryService;
import com.umc.TheGoods.web.dto.post.PostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "포스트 관련된 API 입니다.")
public class PostController {

    final PostQueryService queryService;
    private final MemberQueryService memberQueryService;
    final PostCommandService postCommandService;

    @GetMapping("/")
    @Operation(summary = "인기 사장님 피드 전체 조회 API", description = "포스트의 기본 디폴트 정렬로, 좋아요 순으로 내림차순 정렬합니다. \n\n")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON404", description = "Fail, 이미 존재하는 이름입니다.")
    })
    public ApiResponse<PostResponseDto.PostListViewDto> posts(@RequestParam("like") boolean like) {
        return ApiResponse.onSuccess((PostResponseDto.PostListViewDto) queryService.getPostsOrderByLike());
    }

    @GetMapping("/follow")
    @Operation(summary = "팔로잉 중인 피드 전체 조회 API")
    public ApiResponse<PostResponseDto.PostListViewDto> following(@RequestParam("like") boolean like) {
        return null;
    }

    @PostMapping("/follow/{followingId}")
    @Operation(summary = "팔로우 API", description = "팔로우 하려는 사람의 id를 request로 주시면 됩니다.")
    public ApiResponse<?> follow(Authentication authentication,
                                 @RequestParam(name = "followingId") Long followingId){

        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        //팔로우 기능
        postCommandService.follow(followingId, member);

        return ApiResponse.of(SuccessStatus.POST_FOLLOW_SUCCESS, null);
    }

    @PostMapping("/follow/delete/{followingId}")
    @Operation(summary = "팔로우 취소 API", description = "팔로우 취소하려는 사람의 id를 request로 주시면 됩니다.")
    public ApiResponse<?> deleteFollow(Authentication authentication,
                                       @RequestParam(name = "followingId") Long followingId){

        Long memberId = Long.valueOf(authentication.getName().toString());
        //팔로우 취소 기능
        postCommandService.deleteFollow(followingId,memberId);
        return ApiResponse.of(SuccessStatus.POST_DELETE_FOLLOW_SUCCESS,null);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostViewDto> post(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/")
    public ApiResponse<?> registerPost(@RequestPart(value = "content") String content,
                                       @RequestPart(value = "postImgList", required = false) List<MultipartFile> postImgList,
                                       Authentication authentication) {
        Member member = memberQueryService.findMemberById(Long.valueOf(authentication.getName().toString())).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        postCommandService.registerPost(member,content,postImgList);

        return ApiResponse.of(SuccessStatus.POST_UPLOAD_SUCCESS, null);
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
