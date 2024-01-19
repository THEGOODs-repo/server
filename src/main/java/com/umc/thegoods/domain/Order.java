package com.umc.thegoods.domain;

import com.umc.thegoods.domain.Types.PayType;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;

import javax.persistence.*;

public class Order extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 10)
    private String zipcode;

    @Column(nullable = false, length = 300)
    private String address;

    private String addressDetail;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private PayType payType;

    @Column(nullable = false, length = 30)
    private String refundBank;

    @Column(nullable = false, length = 30)
    private String refundAccount;

    @Column(nullable = false, length = 20)
    private String refundOwner;
}
