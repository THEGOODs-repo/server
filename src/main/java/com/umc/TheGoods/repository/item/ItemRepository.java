package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
