package com.umc.thegoods.domain;

import com.umc.thegoods.domain.Types.OrderCancelReasonType;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderCancel extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private OrderCancelReasonType reasonType;

    @Column(columnDefinition = "TEXT")
    private String reasonDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERDETAIL_ID", nullable = false)
    private OrderDetail orderDetail;
}
