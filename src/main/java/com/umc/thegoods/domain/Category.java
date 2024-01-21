package com.umc.thegoods.domain;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.mapping.Tag.CategoryTag;
import com.umc.thegoods.mapping.user.UserCategory;
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
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<UserCategory> userCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryTag> categoryTagList = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

}
