package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartDetail> cartDetailList = new ArrayList<>();

    // 연관관계 메소드
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getCartList().remove(this);
        }
        this.member = member;
        member.getCartList().add(this);
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getItemCartList().remove(this);
        }
        this.item = item;
        item.getItemCartList().add(this);
    }


}
