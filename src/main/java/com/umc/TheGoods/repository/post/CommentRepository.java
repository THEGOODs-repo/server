package com.umc.TheGoods.repository.post;

import com.umc.TheGoods.domain.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
