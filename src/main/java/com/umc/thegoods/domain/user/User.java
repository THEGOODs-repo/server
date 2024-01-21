package com.umc.thegoods.domain.user;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.Gender;
import com.umc.thegoods.domain.enums.SocialType;
import com.umc.thegoods.domain.enums.UserRole;
import com.umc.thegoods.domain.enums.UserStatus;
import com.umc.thegoods.domain.mypage.*;
import com.umc.thegoods.mapping.user.UserCategory;
import com.umc.thegoods.mapping.user.UserNotification;
import com.umc.thegoods.mapping.user.UserTerm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCategory> userCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserNotification> userNotificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTerm> userTermList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> userAddressList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> userAccountList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Declaration> userDeclarationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Survey> userSurveyList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private ContactTime contactTime;

    @OneToOne(mappedBy = "user")
    private Revenue revenue;

    @OneToOne(mappedBy = "user")
    private WithdrawReason withdrawReason;


}
