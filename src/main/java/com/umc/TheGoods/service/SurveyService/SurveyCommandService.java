package com.umc.TheGoods.service.SurveyService;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;

import java.util.List;

public interface SurveyCommandService {

    List<Item> getPopularItems(Member member);

    List<Member> getPopularSeller(List<Item> item);
}
