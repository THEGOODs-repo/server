package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByMember(Member member, PageRequest pageRequest);

    Page<Item> findAllByNameContaining(String name, PageRequest pageRequest);

    Page<Item> findAllByCategoryName(String name, PageRequest pageRequest);
}
