package com.umc.thegoods.domain.user;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.Gender;
import com.umc.thegoods.domain.enums.SocialType;
import com.umc.thegoods.domain.enums.UserRole;
import com.umc.thegoods.domain.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private UserRole userRole;

    @Column(columnDefinition = "VARCHAR(15)")
    private String nickname;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String email;

    @Column(columnDefinition = "VARCHAR(20)")
    private String password;

    @Column(columnDefinition = "LocalDate")
    private LocalDate birthday;

    @Column(columnDefinition = "VARCHAR(13)")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private UserStatus userStatus;

    @Column(columnDefinition = "TEXT")
    private String kakaoAuth;

    @Column(columnDefinition = "TEXT")
    private String naverAuth;
}
