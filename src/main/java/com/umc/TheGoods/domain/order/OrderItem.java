package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.enums.OrderStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.types.DeliveryType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Integer deliveryFee;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    @ColumnDefault("'PAY_PREV'")
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private DeliveryType deliveryComp;

    @Column(length = 30)
    private String deliveryNum;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverPhone;

    @Column(nullable = false, length = 10)
    private String zipcode;

    @Column(nullable = false, length = 300)
    private String address;

    private String addressDetail;

    private String deliveryMemo;

    @Column(nullable = false, length = 30)
    private String refundBank;

    @Column(nullable = false, length = 30)
    private String refundAccount;

    @Column(nullable = false, length = 20)
    private String refundOwner;

    @Column(length = 30)
    private String depositor;

    private LocalDate depositDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private OrderCancel orderCancel;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private Review review;

    public void setOrders(Orders orders) {
        if (this.orders != null) {
            this.orders.getOrderItemList().remove(this);
        }
        this.orders = orders;
        orders.getOrderItemList().add(this);
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderItemList().remove(this);
        }
        this.item = item;
        item.getOrderItemList().add(this);
    }

    // 주문 상품 합산 금액 업데이트 메소트
    public void updateTotalPrice(Long price) {
        this.totalPrice += price;
    }

    // 주문 상태 업데이트 메소드
    public OrderItem updateStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
        return this;
    }
}
