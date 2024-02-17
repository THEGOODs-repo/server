package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ItemQueryService {

    Optional<Item> findItemById(Long id);

    Optional<ItemOption> findItemOptionById(Long id);

    boolean isExistItem(Long id);

    boolean isExistItemOption(Long id);

    public Page<Item> getMyItemList(Member member, Integer page);

    Page<Item> getTodayItemList(Integer page);

    public Page<Item> getSimilarItemList(Long itemId, Member member, Integer page);

    Page<Item> getTopSaleItemList(Integer page);

    Page<Item> getSteadySaleItemList(Integer page);

    public Page<Item> getItemByTagCount(Integer page);

    public Page<Item> getItemByDeliveryDate(Integer page);

    public Page<Item> searchItem(Member member, String itemName, String categoryName, String sellerName, List<String> tagName, Integer page);

    Page<Item> getMainItem(String type, Integer page);

    Page<Item> getRelatedItem(Long itemId, Integer page);

}
