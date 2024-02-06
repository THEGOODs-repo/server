package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.images.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemId(Long itemId);
}
