package com.umc.TheGoods.web.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SurveyResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopularSellerResultDTO {
        String profile;
        String nickname;
        String introduce;
        List<Long> tagList;
    }
}
