package com.umc.thegoods.domain;

import com.umc.thegoods.domain.Types.OrderCancelReasonType;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OrderCancel extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private OrderCancelReasonType reasonType;

    @Column(columnDefinition = "TEXT")
    private String reasonDetail;
    
}
