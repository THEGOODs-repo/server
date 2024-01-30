package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.item.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
