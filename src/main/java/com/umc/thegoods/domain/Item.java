package com.umc.thegoods.domain;

import com.umc.thegoods.domain.Types.DeliveryType;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.ItemStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private ItemStatus status;

    @Column(nullable = false, length = 6)
    private Integer stock;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 6)
    private Integer deliveryFee;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private DeliveryType deliveryType;

    @Column(nullable = false, length = 2)
    private Integer delivery_date;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isLimitless;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Long dibsCount;

    @Column(nullable = false)
    private Long salesCount;

}
