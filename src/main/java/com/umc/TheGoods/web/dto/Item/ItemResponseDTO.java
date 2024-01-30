package com.umc.TheGoods.web.dto.Item;

import lombok.*;

import java.time.LocalDateTime;

public class ItemResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadItemResultDTO{
        Long itemId;
        LocalDateTime createdAt;
    }

}
