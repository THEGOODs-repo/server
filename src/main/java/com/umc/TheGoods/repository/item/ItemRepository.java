package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {


    Page<Item> findAllByMember(Member member, PageRequest pageRequest);

    Page<Item> findAllByNameContaining(String name, PageRequest pageRequest);

    Page<Item> findAllByCategoryName(String name, PageRequest pageRequest);

    Page<Item> findAllByItemTagListTagIn(List<Tag> tags, PageRequest pageRequest);

    //Page<Item> findAllByItemTagListIn(List<ItemTag> itemTagList, PageRequest pageRequest);

    @Query("SELECT item FROM Item item WHERE item.category = :category ORDER BY (item.viewCount + item.salesCount) DESC")
    List<Item> findPopularItemList(Pageable pageable, Category category);


}
