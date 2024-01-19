package com.umc.thegoods.domain.images;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ItemImg extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private Boolean thumbnail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;
}
