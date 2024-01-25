package com.umc.thegoods.repository.item;

import com.umc.thegoods.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
