package com.umc.TheGoods.domain.mypage;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.*;


/**
 * 탈퇴 이유 Entity
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WithdrawReason extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdraw_reason_id")
    private Long id;


    @Column(nullable = false, columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean caution; //주의 사항 체크



    @Column(nullable = false)
    private Integer reason; // 탈퇴 사유


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
