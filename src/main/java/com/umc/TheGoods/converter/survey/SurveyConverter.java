package com.umc.TheGoods.converter.survey;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.survey.SurveyResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyConverter {

    public static List<SurveyResponseDTO.PopularSellerResultDTO> toPopularSellerResultDTO(List<Member> member, List<Item> item) {


        return member.stream()
                .flatMap(m -> item.stream().map(i -> SurveyResponseDTO.PopularSellerResultDTO.builder()
                        .nickname(m.getNickname())
                        .introduce(m.getIntroduce())
                        .profile(m.getProfileImg() != null ? m.getProfileImg().getUrl() : null)
                        .tagList(i.getItemTagList().stream().map(t -> t.getTag().getId()).collect(Collectors.toList()))
                        .build())).collect(Collectors.toList());

    }

}
