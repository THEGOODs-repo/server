package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.TagHandler;
import com.umc.TheGoods.converter.item.*;
import com.umc.TheGoods.domain.images.ItemImg;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.TagRepository;
import com.umc.TheGoods.repository.item.ItemImgRepository;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.item.ItemViewRepository;
import com.umc.TheGoods.service.CategoryService.CategoryQueryService;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCommandServiceImpl implements ItemCommandService {

    private final CategoryQueryService categoryQueryService;
    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemViewRepository itemViewRepository;

    @Override
    @Transactional
    public Item uploadItem(Member member, ItemRequestDTO.UploadItemDTO request) {

        Item newItem = ItemConverter.toItem(request);

        Category category = categoryQueryService.findCategoryById(request.getCategory());

        newItem.setCategory(category);
        newItem.setMember(member);

        List<Tag> tagList = request.getItemTag().stream()
                .map(tag -> {
                    return tagRepository.findById(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND));
                }).collect(Collectors.toList());

        List<ItemTag> itemTagList = ItemTagConverter.toItemTagList(tagList);

        itemTagList.forEach(itemTag -> {
            itemTag.setItem(newItem);
        });

//        for (Tag tag : tagList) {
//            itemTagList.forEach(itemTag -> itemTag.setTag(tag));
//        }

        List<ItemImg> itemImgList = request.getItemImgUrlList().stream().map(
                itemImgDTO -> ItemImageConverter.toItemImg(itemImgDTO)).collect(Collectors.toList()
        );
        for (ItemImg itemImg : itemImgList) {
            itemImg.setItem(newItem);
        }

        List<ItemOption> itemOptionList = request.getItemOptionList().stream().map(
                itemOptionDTO -> ItemOptionConverter.toItemOption(itemOptionDTO)).collect(Collectors.toList()
        );
        for (ItemOption itemOption : itemOptionList) {
            itemOption.setItem(newItem);
        }

        itemRepository.save(newItem);
        itemImgRepository.saveAll(itemImgList);
        itemOptionRepository.saveAll(itemOptionList);
        return newItem;
    }

    @Override
    @Transactional
    public Item getItemContent(Long itemId, Member member) {

        Item itemContent = itemRepository.findById(itemId).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_NOT_FOUND));

        if (member != itemContent.getMember()) {
            itemContent.updateViewCounts();
            ItemView itemView = ItemViewConverter.toItemView(member, itemContent);
            itemViewRepository.save(itemView);
        }

        return itemContent;
    }
}
