package com.umc.TheGoods.domain.mapping.member;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.Category;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.web.dto.member.MemberResponseDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCategory extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setMember(Member member) {
        this.member = member;
        member.getMemberCategoryList().add(this); // Add the current MemberCategory to the new Member

    }

}
