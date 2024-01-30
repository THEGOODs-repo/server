package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.item.Review;
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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private OrderStatus status;

    @Column(length = 30)
    private String deliveryNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private OrderCancel orderCancel;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Review review;

    // 연관관계 메소드
    public void setOrders(Orders orders) {
        if (this.orders != null) {
            this.orders.getOrderDetailList().remove(this);
        }
        this.orders = orders;
        orders.getOrderDetailList().add(this);
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderDetailList().remove(this);
        }
        this.item = item;
        item.getOrderDetailList().add(this);
    }

    public void setItemOption(ItemOption itemOption) {
        if (this.itemOption != null) {
            this.itemOption.getOrderDetailList().remove(this);
        }
        this.itemOption = itemOption;
        itemOption.getOrderDetailList().add(this);
    }
}
