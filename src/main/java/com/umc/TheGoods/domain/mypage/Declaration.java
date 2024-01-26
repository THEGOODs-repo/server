package com.umc.TheGoods.domain.mypage;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;

/**
 * 신고 Entity
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Declaration extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaration_id")
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String type; //신고 항목

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String reason; //신고 사유

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String salesPost; //판매글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
