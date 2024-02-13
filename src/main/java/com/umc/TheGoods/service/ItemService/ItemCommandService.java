package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemCommandService {

    public Item uploadItem(Member member, ItemRequestDTO.UploadItemDTO request, MultipartFile itemThumbnail, List<MultipartFile> multipartFileList);

    public Item updateItem(Long itemId, Member member, ItemRequestDTO.UpdateItemDTO request, MultipartFile itemThumbnail, List<MultipartFile> multipartFileList);

    public Item getItemContent(Long itemId, Member member);
}
