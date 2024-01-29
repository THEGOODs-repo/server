package com.umc.TheGoods.service.CategoryService;

import com.umc.TheGoods.domain.item.Category;

public interface CategoryQueryService {

    public Category findCategoryById(Long categoryId);
}
