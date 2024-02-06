package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

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

    public void setItemOption(ItemOption itemOption) {
        if (this.itemOption != null) {
            this.itemOption.getCartList().remove(this);
        }
        this.itemOption = itemOption;
        itemOption.getCartList().add(this);
    }

    public Cart updateAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
