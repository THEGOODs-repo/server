package com.umc.TheGoods.repository.post;

import com.umc.TheGoods.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    //Page<Post> getPostsOrderByLikes(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.likesCount = p.likesCount +1 WHERE p.id= :postId")
    void updateLikeCount(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("update Post p SET p.likesCount = p.likesCount -1 WHERE p.id= :postId")
    void updateUnlikeCount(Long postId);


}
