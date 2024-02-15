package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.mapping.Tag.CategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, Long> {
}
