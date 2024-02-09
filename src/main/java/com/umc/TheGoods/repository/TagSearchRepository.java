package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.mapping.ViewSearch.TagSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagSearchRepository extends JpaRepository<TagSearch, Long> {
}
