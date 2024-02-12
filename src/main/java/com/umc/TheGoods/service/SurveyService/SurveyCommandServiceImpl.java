package com.umc.TheGoods.service.SurveyService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.repository.item.ItemRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyCommandServiceImpl implements SurveyCommandService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Item> getPopularItems(Member member) {

        PageRequest pageRequest = PageRequest.of(0, 3);

        List<Item> item = itemRepository.findPopularItemList(pageRequest, member.getMemberCategoryList().get(0).getCategory());

        return item;
    }

    @Override
    public List<Member> getPopularSeller(List<Item> item) {

        List<Member> member = item.stream().map(i -> i.getMember())
                .collect(Collectors.toList());

        return member;
    }
}
