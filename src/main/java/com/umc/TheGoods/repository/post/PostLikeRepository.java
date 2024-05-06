package com.umc.TheGoods.repository.post;


import com.umc.TheGoods.domain.mapping.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByMember_IdAndPost_Id(Long memberId, Long postId);
}
