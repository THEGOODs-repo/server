package com.umc.thegoods.domain.mypage;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContactTime extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_time_id")
    private Long id;

    private Integer startTime;

    private Integer endTime;

    @Column(columnDefinition = "VARCHAR(1) DEFAULT 'O'")
    private String allTime;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
