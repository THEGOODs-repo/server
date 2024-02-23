package com.umc.TheGoods.redis.repository;

import com.umc.TheGoods.redis.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
