package com.umc.TheGoods.converter.survey;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.survey.SurveyResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyConverter {

    public static List<SurveyResponseDTO.PopularSellerResultDTO> toPopularSellerResultDTO(List<Member> member, List<List<Item>> item) {


        return member.stream()
                .flatMap(m -> item.stream().map(i -> SurveyResponseDTO.PopularSellerResultDTO.builder()
                        .nickname(m.getNickname())
                        .introduce(m.getIntroduce())
                        .profile(m.getProfileImg() != null ? m.getProfileImg().getUrl() : null)
                        .tagList(i.stream()
                                .flatMap(tag -> tag.getItemTagList().stream()
                                        .map(itemTerm -> itemTerm.getTag().getId())).collect(Collectors.toList())
                        )
                        .url1(i.get(0).getItemImgList().get(0).getUrl())
                        .url2(i.get(1).getItemImgList().get(0).getUrl())
                        .build())).collect(Collectors.toList());

    }

    public static List<SurveyResponseDTO.PopularItemResultDTO> toPopularItemResultDTO(List<Item> item) {
        return item.stream()
                .map(i -> SurveyResponseDTO.PopularItemResultDTO.builder()
                        .img(i.getItemImgList().get(0).getUrl())
                        .tag("#" + i.getItemTagList().get(0).getTag().getName())
                        .tagList(i.getItemTagList().stream().map(t -> t.getTag().getId()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }

}
