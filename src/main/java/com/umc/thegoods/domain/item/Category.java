package com.umc.thegoods.domain.item;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.mapping.Tag.CategoryTag;
import com.umc.thegoods.mapping.member.MemberCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<MemberCategory> memberCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryTag> categoryTagList = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

}
