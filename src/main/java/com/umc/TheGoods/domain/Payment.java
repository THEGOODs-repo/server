package com.umc.TheGoods.domain;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * TODO
     * 후순위 개발 시
     * -> Payment 엔티티 설정 필요
     */
}
