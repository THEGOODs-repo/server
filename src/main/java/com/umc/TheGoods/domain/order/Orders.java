package com.umc.TheGoods.domain.order;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.types.PayType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Orders extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList = new ArrayList<>();
}
