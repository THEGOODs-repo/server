package com.umc.TheGoods.redis.repository;

import com.umc.TheGoods.redis.domain.LoginStatus;
import org.springframework.data.repository.CrudRepository;

public interface LoginStatusRepository extends CrudRepository<LoginStatus, String> {
}
