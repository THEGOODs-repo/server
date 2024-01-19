package com.umc.thegoods.domain;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ItemOption extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 6)
    private Integer stock;

}
