package com.umc.TheGoods.domain.mypage;


import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Account extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String bankName;


    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    private String accountNum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
