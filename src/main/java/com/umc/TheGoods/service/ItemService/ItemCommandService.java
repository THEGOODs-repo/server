package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;

public interface ItemCommandService {

    public Item uploadItem(Member member, ItemRequestDTO.UploadItemDTO request);
}
