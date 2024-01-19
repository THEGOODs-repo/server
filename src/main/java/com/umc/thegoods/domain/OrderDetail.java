package com.umc.thegoods.domain;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.OrderStatus;

import javax.persistence.*;

public class OrderDetail extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
