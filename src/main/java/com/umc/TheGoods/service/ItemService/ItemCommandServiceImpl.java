package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.TagHandler;
import com.umc.TheGoods.converter.item.*;
import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.images.ItemImg;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.TagRepository;
import com.umc.TheGoods.repository.item.*;
import com.umc.TheGoods.service.CategoryService.CategoryQueryService;
import com.umc.TheGoods.service.UtilService;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ItemTagRepository itemTagRepository;
    private final UtilService utilService;

    @Override
    @Transactional
    public Item uploadItem(Member member, ItemRequestDTO.UploadItemDTO request, MultipartFile itemThumbnail, List<MultipartFile> multipartFileList) {

        if (member.getMemberRole() != MemberRole.SELLER) {
            throw new ItemHandler(ErrorStatus.ITEM_NOT_SELLER);
        }

        Item newItem = ItemConverter.toItem(request);

        Category category = categoryQueryService.findCategoryById(request.getCategory());

        newItem.setCategory(category);
        newItem.setMember(member);

        itemRepository.save(newItem);

        List<Tag> tagList = request.getItemTag().stream()
                .map(tag -> {
                    return tagRepository.findById(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND));
                }).collect(Collectors.toList());

        newItem.updateTagCounts(tagList.size());

        List<ItemTag> itemTagList = ItemTagConverter.toItemTagList(tagList);

        itemTagList.forEach(itemTag -> {
            itemTag.setItem(newItem);
        });

//        for (Tag tag : tagList) {
//            itemTagList.forEach(itemTag -> itemTag.setTag(tag));
//        }

        String thumbnailUrl = utilService.uploadS3Img("item", itemThumbnail);
        ItemImg thumbnailItemImg = ItemImageConverter.toItemImg(thumbnailUrl, true);
        thumbnailItemImg.setItem(newItem);
        itemImgRepository.save(thumbnailItemImg);

        // 일반 상품 이미지
        if (multipartFileList != null) {
            List<ItemImg> itemImgList = multipartFileList.stream().map(multipartFile -> {
                String itemImgUrl = utilService.uploadS3Img("item", multipartFile);

                ItemImg itemImg = ItemImageConverter.toItemImg(itemImgUrl, false);
                itemImg.setItem(newItem);
                return itemImg;
            }).collect(Collectors.toList());

            itemImgRepository.saveAll(itemImgList);
        }

//        List<ItemImg> itemImgList = request.getItemImgUrlList().stream().map(
//                itemImgDTO -> ItemImageConverter.toItemImg(itemImgDTO)).collect(Collectors.toList()
//        );
//        for (ItemImg itemImg : itemImgList) {
//            itemImg.setItem(newItem);
//        }

        List<ItemOption> itemOptionList = request.getItemOptionList().stream().map(
                itemOptionDTO -> ItemOptionConverter.toItemOption(itemOptionDTO)).collect(Collectors.toList()
        );
        for (ItemOption itemOption : itemOptionList) {
            itemOption.setItem(newItem);
        }

        itemOptionRepository.saveAll(itemOptionList);
        return newItem;
    }

    @Override
    @Transactional
    public Item updateItem(Long itemId, Member member, ItemRequestDTO.UpdateItemDTO request, MultipartFile itemThumbnail, List<MultipartFile> multipartFileList) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_NOT_FOUND));

        if (member != item.getMember()) {
            new ItemHandler(ErrorStatus.ITEM_UPDATE_FAIL);
        }

        item.updateItem(request);

        List<ItemTag> itemTagList = itemTagRepository.findByItemId(itemId);
        List<ItemImg> itemImgList = itemImgRepository.findByItemId(itemId);
        List<ItemOption> itemOptionList = itemOptionRepository.findByItemId(itemId);

        itemTagRepository.deleteAll(itemTagList);

        Category newCategory = categoryQueryService.findCategoryById(request.getCategory());

        item.setCategory(newCategory);

        List<Tag> newTagList = request.getItemTag().stream()
                .map(tag -> {
                    return tagRepository.findById(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND));
                }).collect(Collectors.toList());

        item.updateTagCounts(newTagList.size());

        List<ItemTag> newItemTagList = ItemTagConverter.toItemTagList(newTagList);

        newItemTagList.forEach(itemTag -> {
            itemTag.setItem(item);
        });

//        for (Tag tag : tagList) {
//            itemTagList.forEach(itemTag -> itemTag.setTag(tag));
//        }

        itemImgRepository.deleteAll(itemImgList);

        String thumbnailUrl = utilService.uploadS3Img("item", itemThumbnail);
        ItemImg thumbnailItemImg = ItemImageConverter.toItemImg(thumbnailUrl, true);
        thumbnailItemImg.setItem(item);
        itemImgRepository.save(thumbnailItemImg);

        // 일반 상품 이미지
        if (multipartFileList != null) {
            List<ItemImg> newItemImgList = multipartFileList.stream().map(multipartFile -> {
                String itemImgUrl = utilService.uploadS3Img("item", multipartFile);

                ItemImg itemImg = ItemImageConverter.toItemImg(itemImgUrl, false);
                itemImg.setItem(item);
                return itemImg;
            }).collect(Collectors.toList());

            itemImgRepository.saveAll(newItemImgList);
        }

//        List<ItemImg> newItemImgList = request.getItemImgUrlList().stream().map(
//                itemImgDTO -> ItemImageConverter.toItemImg(itemImgDTO)).collect(Collectors.toList()
//        );
//        for (ItemImg itemImg : newItemImgList) {
//            itemImg.setItem(item);
//        }

        List<ItemOption> newItemOptionList = request.getItemOptionList().stream().map(
                itemOptionDTO -> ItemOptionConverter.toItemOption(itemOptionDTO)).collect(Collectors.toList()
        );
        for (ItemOption itemOption : newItemOptionList) {
            itemOption.setItem(item);
        }

        itemOptionRepository.deleteAll(itemOptionList);

        itemOptionRepository.saveAll(newItemOptionList);
        return item;
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
