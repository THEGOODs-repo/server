package com.umc.thegoods.domain.images;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemImg extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private Boolean thumbnail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}