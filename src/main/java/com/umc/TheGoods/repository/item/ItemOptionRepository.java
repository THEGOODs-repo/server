package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {

    List<ItemOption> findByItemId(Long itemId);
}
