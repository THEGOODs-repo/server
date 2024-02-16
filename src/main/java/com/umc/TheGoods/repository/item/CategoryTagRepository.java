package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.CategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, Long> {

    Optional<CategoryTag> findByCategoryAndTag(Category category, Tag tag);
}
