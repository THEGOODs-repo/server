package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.web.dto.item.ConsultResultDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemTagRepository extends JpaRepository<ItemTag, Long> {

    List<ItemTag> findByItemId(Long itemId);

    ItemTag findByTagId(Long tagId);

    @Query("SELECT NEW com.umc.TheGoods.web.dto.item.ConsultResultDTO(it.item.id,COUNT(it.tag.id)) " +
            "FROM ItemTag AS it " +
            "GROUP BY it.item.id " +
            "ORDER BY COUNT(it.tag.id) DESC")
    List<ConsultResultDTO> countTagsByTagId();
}
