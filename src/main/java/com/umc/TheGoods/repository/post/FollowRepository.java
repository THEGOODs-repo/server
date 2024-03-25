package com.umc.TheGoods.repository.post;

import com.umc.TheGoods.domain.member.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowingIdAndFollowerId(Long followingId, Long followerId);
}
