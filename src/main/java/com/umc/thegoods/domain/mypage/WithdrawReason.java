package com.umc.thegoods.domain.mypage;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.member.Member;
import lombok.*;

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

    @Column(nullable = false, columnDefinition = "VARCHAR(1)")
    private String caution; //주의 사항 체크


    @Column(nullable = false)
    private Integer reason; // 탈퇴 사유


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
