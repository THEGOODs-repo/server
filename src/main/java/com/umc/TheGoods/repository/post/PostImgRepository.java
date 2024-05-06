package com.umc.TheGoods.repository.post;

import com.umc.TheGoods.domain.images.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {
    List<PostImg> findByPostId(Long postId);
}
