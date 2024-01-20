package com.umc.thegoods.mapping.user;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserNotification extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //알람 동의 여부
    @Column(nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'X'")
    private String agree;
}
