package com.umc.thegoods.domain.order;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.OrderStatus;
import com.umc.thegoods.domain.item.Item;
import com.umc.thegoods.domain.item.ItemOption;
import com.umc.thegoods.domain.item.Review;
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
    @JoinColumn(name = "item_option_id", nullable = false)
    private ItemOption itemOption;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private OrderCancel orderCancel;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Review review;
}
