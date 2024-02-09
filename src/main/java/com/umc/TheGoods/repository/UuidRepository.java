package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.images.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
