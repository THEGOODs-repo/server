package com.umc.thegoods.domain.user;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.mapping.user.UserNotification;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<UserNotification> memberMissionList = new ArrayList<>();
}
