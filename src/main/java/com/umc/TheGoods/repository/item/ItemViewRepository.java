package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemViewRepository extends JpaRepository<ItemView, Long> {

    List<ItemView> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);
}
