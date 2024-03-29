package com.umc.TheGoods.domain.mapping.ViewSearch;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TagSearch extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_search_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void setMember(Member member) {
        if (this.member != null)
            member.getTagSearchList().remove(this);
        this.member = member;
        member.getTagSearchList().add(this);
    }

    public void setTag(Tag tag) {
        if (this.tag != null)
            tag.getTagSearchList().remove(this);
        this.tag = tag;
        tag.getTagSearchList().add(this);
    }
}
