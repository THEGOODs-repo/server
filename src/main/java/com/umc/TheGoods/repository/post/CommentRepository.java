package com.umc.TheGoods.repository.post;

import com.umc.TheGoods.domain.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Comment c SET c.content= :comment WHERE c.id= :commentId")
    void updateComment(Long commentId, String comment);
}
