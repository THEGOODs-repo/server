package com.umc.thegoods.domain.order;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.types.OrderCancelReasonType;
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
