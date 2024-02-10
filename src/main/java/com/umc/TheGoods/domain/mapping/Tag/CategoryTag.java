package com.umc.TheGoods.domain.mapping.Tag;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.item.Tag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryTag extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // 연관관계 메소드
    public void setCategory(Category category) {
        if (this.category != null) {
            category.getCategoryTagList().remove(this);
        }
        this.category = category;
        category.getCategoryTagList().add(this);

    }

    public void setTag(Tag tag) {
        if (this.tag != null)
            tag.getCategoryTagList().remove(this);
        this.tag = tag;
        tag.getCategoryTagList().add(this);
    }
}
