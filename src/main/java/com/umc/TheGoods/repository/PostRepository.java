package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //Page<Post> getPostsOrderByLikes(Pageable pageable);
}
