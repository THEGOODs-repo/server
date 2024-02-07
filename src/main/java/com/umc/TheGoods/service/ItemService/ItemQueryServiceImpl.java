package com.umc.TheGoods.service.ItemService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.item.ItemOptionRepository;
import com.umc.TheGoods.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

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
}
