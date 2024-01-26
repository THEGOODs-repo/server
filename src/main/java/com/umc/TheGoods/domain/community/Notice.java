package com.umc.TheGoods.domain.community;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.images.NoticeImg;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.types.NoticeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class Notice extends BaseDateTimeEntity {
    // user: admin

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

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<NoticeImg> noticeImgList = new ArrayList<>();
}
