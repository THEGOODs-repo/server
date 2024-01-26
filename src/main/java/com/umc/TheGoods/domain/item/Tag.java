package com.umc.TheGoods.domain.item;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.mapping.Tag.CategoryTag;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.domain.mapping.ViewSearch.TagSearch;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tag extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<CategoryTag> categoryTagList = new ArrayList<>();

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<ItemTag> itemTagList = new ArrayList<>();

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagSearch> tagSearchList = new ArrayList<>();
}
