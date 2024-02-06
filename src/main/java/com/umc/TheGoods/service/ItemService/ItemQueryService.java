package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;

import java.util.Optional;

public interface ItemQueryService {

    Optional<Item> findItemById(Long id);

    Optional<ItemOption> findItemOptionById(Long id);

    boolean isExistItem(Long id);

    boolean isExistItemOption(Long id);

}
