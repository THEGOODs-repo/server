package com.umc.TheGoods.domain.mypage;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


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

    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean allTime;


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
