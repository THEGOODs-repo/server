package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.item.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    Optional<Tag> findFirstByNameOrderByCreatedAtAsc(String name);

    boolean existsByName(String name);
}
