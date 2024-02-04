package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import org.springframework.security.core.Authentication;

public interface ItemCommandService {

    public Item uploadItem(Member member, ItemRequestDTO.UploadItemDTO request);

    public Item getItemContent(Long itemId, Authentication authentication);
}
