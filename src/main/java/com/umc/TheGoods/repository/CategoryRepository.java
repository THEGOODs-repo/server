package com.umc.TheGoods.repository;

import com.umc.TheGoods.domain.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
