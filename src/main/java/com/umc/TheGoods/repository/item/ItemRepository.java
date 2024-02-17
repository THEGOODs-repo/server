package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByMember(Member member, PageRequest pageRequest);

    Page<Item> findAllByNameContaining(String name, PageRequest pageRequest);

    Page<Item> findAllByCategoryName(String name, PageRequest pageRequest);

    List<Item> findAllByItemTagListTagIn(List<Tag> tags);

    //Page<Item> findAllByItemTagListIn(List<ItemTag> itemTagList, PageRequest pageRequest);

    Page<Item> findAllByEndDateGreaterThanEqual(LocalDate localDate, PageRequest pageRequest);
}
