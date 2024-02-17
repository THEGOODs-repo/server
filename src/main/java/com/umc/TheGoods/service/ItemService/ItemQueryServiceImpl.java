package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.CategoryHandler;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.apiPayload.exception.handler.TagHandler;
import com.umc.TheGoods.converter.tag.TagSearchConverter;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.mapping.ViewSearch.TagSearch;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.TagRepository;
import com.umc.TheGoods.repository.TagSearchRepository;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.item.ItemTagRepository;
import com.umc.TheGoods.repository.item.ItemViewRepository;
import com.umc.TheGoods.repository.member.CategoryRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemViewRepository itemViewRepository;
    private final ItemTagRepository itemTagRepository;
    private final TagSearchRepository tagSearchRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    Integer pageSize = 10;

    @Override
    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Optional<ItemOption> findItemOptionById(Long id) {
        return itemOptionRepository.findById(id);
    }

    @Override
    public boolean isExistItem(Long id) {
        return itemRepository.existsById(id);
    }

    @Override
    public boolean isExistItemOption(Long id) {
        return itemOptionRepository.existsById(id);
    }

    @Override
    @Transactional
    public Page<Item> getMyItemList(Member member, Integer page) {
        Page<Item> itemPage = itemRepository.findAllByMember(member, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
        return itemPage;
    }

    @Override
    @Transactional
    public Page<Item> getSimilarItemList(Long itemId, Member member, Integer page) {
        Page<Item> similarItemList = null;

        if (!member.getNickname().equals("no_login_user")) {
            List<ItemView> lastView = itemViewRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId());
            if (!lastView.isEmpty()) {
                Item viewedItem = itemRepository.findById(lastView.get(0).getItem().getId()).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_NOT_FOUND));
                Category category = viewedItem.getCategory();
                similarItemList = itemRepository.findAllByCategoryName(category.getName(), PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "viewCount")));
            } else {
                //추후 수정
                similarItemList = itemRepository.findAllByCategoryName("창작", PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "viewCount")));
            }
        } else {
            Item viewedItem = itemRepository.findById(itemId).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_NOT_FOUND));
            Category category = viewedItem.getCategory();
            similarItemList = itemRepository.findAllByCategoryName(category.getName(), PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "viewCount")));
        }

        return similarItemList;
    }

    @Override
    public Page<Item> getTopSaleItemList(Integer page) {

        return itemRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "salesCount")));

    }

    @Override
    public Page<Item> getSteadySaleItemList(Integer pageIdx) {
        LocalDate date = LocalDate.now().minusMonths(6);
        return itemRepository.findAllByStartDateLessThanEqual(date, PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "viewCount")));
    }

    @Override
    @Transactional
    public Page<Item> getItemByTagCount(Integer page) {
//        List<ConsultResultDTO> consultResults = itemTagRepository.countTagsByTagId();
//
//        List<Item> itemList = consultResults.stream()
//                .map(consultResult -> itemRepository.findById(consultResult.getItemId()).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEM_NOT_FOUND)))
//                .collect(Collectors.toList());
//
//        PageRequest pageRequest = PageRequest.of(page, 10);
//        int start = (int) pageRequest.getOffset();
//        int end = Math.min((start + pageRequest.getPageSize()), itemList.size());
//        Page<Item> itemPage = new PageImpl<>(itemList.subList(start, end), pageRequest, itemList.size());

        Page<Item> itemPage = null;
        itemPage = itemRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "tagsCount")));

        return itemPage;
    }

    @Override
    @Transactional
    public Page<Item> getItemByDeliveryDate(Integer page) {
        Page<Item> itemPage = itemRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "deliveryDate")));
        return itemPage;
    }

    @Override
    @Transactional
    public Page<Item> searchItem(Member member, String itemName, String categoryName, String sellerName, List<String> tagName, Integer page) {
        Page<Item> itemPage = null;
        Integer searchCondition = 0;

        if (itemName != null) {
            itemPage = itemRepository.findAllByNameContaining(itemName, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
            itemPage = itemRepository.findAllByCategoryName(category.getName(), PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (sellerName != null) {
            Member seller = memberRepository.findByNickname(sellerName).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
            itemPage = itemRepository.findAllByMember(seller, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (tagName != null) {
            //이 방식대로 tag 탐색시 잘못 탐색됨. tag 전체를 가지고 있는 경우만 찾도록 해야함.
            List<String> tagNameWithoutHashtag = tagName.stream()
                    .map(tag -> tag.replace("#", ""))
                    .collect(Collectors.toList());

            List<Tag> tags = tagNameWithoutHashtag.stream()
                    .map(tag -> tagRepository.findByName(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND)))
                    .collect(Collectors.toList());

            if (!tags.isEmpty()) {
                // 아이템을 검색하는 메소드 호출
                List<Item> itemList = itemRepository.findAllByItemTagListTagIn(tags);
                searchCondition++;

                // 중복된 아이템을 아이디를 기준으로 제거
                List<Item> distinctItems = itemList.stream()
                        .collect(Collectors.toMap(Item::getId, item -> item, (existing, replacement) -> existing))
                        .values()
                        .stream()
                        .collect(Collectors.toList());

                System.out.println("test size : " + distinctItems.size());

                // 중복이 제거된 아이템으로 새로운 Page 생성
                PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), distinctItems.size());
                itemPage = new PageImpl<>(distinctItems.subList(start, end), pageRequest, distinctItems.size());
                //itemPage = new PageImpl<>(distinctItems, itemPage.getPageable(), distinctItems.size());
            }
            List<TagSearch> tagSearchList = TagSearchConverter.toTagSearchList(tags, member);

            tagSearchList.forEach(tagSearch -> {
                tagSearch.setMember(member);
            });

            tagSearchRepository.saveAll(tagSearchList);
        }
        if (searchCondition > 1) {
            throw new ItemHandler(ErrorStatus.ITEM_SEARCH_ERROR);
        }

        return itemPage;
    }

    @Override
    public Page<Item> getMainItem(String type, Integer pageIdx) {
        Page<Item> itemPage = null;
        if (type.equals("new")) {
            itemPage = itemRepository.findAll(PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
        } else if (type.equals("popular")) {
            itemPage = itemRepository.findAll(PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.DESC, "viewCount")));
        } else if (type.equals("last")) {
            itemPage = itemRepository.findAllByEndDateGreaterThanEqual(LocalDate.now(), PageRequest.of(pageIdx, pageSize, Sort.by(Sort.Direction.ASC, "endDate")));
        } else {
            throw new ItemHandler(ErrorStatus.MAIN_ITEM_SEARCH_TYPE_ERROR);
        }

        return itemPage;
    }
}
