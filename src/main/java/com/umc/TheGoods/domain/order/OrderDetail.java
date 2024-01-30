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
public class OrderDetail extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Long orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    public void setOrderItem(OrderItem orderItem) {
        if (this.orderItem != null) {
            this.orderItem.getOrderDetailList().remove(this);
        }
        this.orderItem = orderItem;
        orderItem.getOrderDetailList().add(this);
    }

    public void setItemOption(ItemOption itemOption) {
        if (this.itemOption != null) {
            this.itemOption.getOrderDetailList().remove(this);
        }
        this.itemOption = itemOption;
        itemOption.getOrderDetailList().add(this);
    }

}
