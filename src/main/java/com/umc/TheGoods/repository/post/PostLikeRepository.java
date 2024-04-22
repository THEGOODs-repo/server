package com.umc.TheGoods.repository.post;


import com.umc.TheGoods.domain.mapping.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByPostIdAndMemberId(Long postId, Long memberId);
}
