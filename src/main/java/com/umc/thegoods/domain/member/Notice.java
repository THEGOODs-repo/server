package com.umc.thegoods.domain.member;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.types.NoticeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class Notice extends BaseDateTimeEntity {
    // user: 관리자 권한

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private NoticeType type;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String writer;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
