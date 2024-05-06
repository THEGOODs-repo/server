package com.umc.TheGoods.repository.post;


import com.umc.TheGoods.domain.mapping.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByMember_IdAndComment_Id(Long memberId, Long commentId);
}
