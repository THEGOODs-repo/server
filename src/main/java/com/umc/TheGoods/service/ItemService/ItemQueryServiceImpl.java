package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.apiPayload.exception.handler.TagHandler;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.TagRepository;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.item.ItemTagRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ItemTagRepository itemTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

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
    public Page<Item> searchItem(Member member, String itemName, String categoryName, String sellerName, List<String> tagName, Integer page) {
        Page<Item> itemPage = null;
        Integer searchCondition = 0;

        if (itemName != null) {
            itemPage = itemRepository.findAllByNameContaining(itemName, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (categoryName != null) {
            itemPage = itemRepository.findAllByCategoryName(categoryName, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (sellerName != null) {
            Member seller = memberRepository.findByNickname(sellerName).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
            itemPage = itemRepository.findAllByMember(seller, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            searchCondition++;
        }
        if (tagName != null) {
            //이 방식대로 tag 탐색시 잘못 탐색됨. tag 전체를 가지고 있는 경우만 찾도록 해야함.
            List<Tag> tags = tagName.stream()
                    .map(tag -> tagRepository.findByName(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND)))
                    .collect(Collectors.toList());

            if (!tags.isEmpty()) {
                // 아이템을 검색하는 메소드 호출
                itemPage = itemRepository.findAllByItemTagListTagIn(tags, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
                searchCondition++;
            }
//            List<ItemTag> newTagList = tagName.stream()
//                    .map(tag -> {
//                        return itemTagRepository.findByTagId(tagRepository.findByName(tag).orElseThrow(() -> new TagHandler(ErrorStatus.TAG_NOT_FOUND)).getId());
//                    }).distinct().collect(Collectors.toList());
//
//            itemPage = itemRepository.findAllByItemTagListIn(newTagList, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
//            searchCondition++;
        }
        if (searchCondition > 1) {
            throw new ItemHandler(ErrorStatus.ITEM_SEARCH_ERROR);
        }

        return itemPage;
    }
}
