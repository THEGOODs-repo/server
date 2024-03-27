package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.item.ItemOption;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartDetail extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    public void setCart(Cart cart) {
        if (this.cart != null) {
            this.cart.getCartDetailList().remove(this);
        }
        this.cart = cart;
        cart.getCartDetailList().add(this);
    }

    public void detachCart() {
        if (this.cart != null) {
            this.cart.getCartDetailList().remove(this);
            this.cart = null;
        }
    }

    public void detachItemOption() {
        if (this.itemOption != null) {
            this.itemOption.getCartDetailList().remove(this);
            this.itemOption = null;
        }
    }

    public void setItemOption(ItemOption itemOption) {
        if (this.itemOption != null) {
            this.itemOption.getCartDetailList().remove(this);
        }
        this.itemOption = itemOption;
        itemOption.getCartDetailList().add(this);
    }

    public CartDetail updateAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
