package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTagRepository extends JpaRepository<ItemTag, Long> {

    List<ItemTag> findByItemId(Long itemId);
}
