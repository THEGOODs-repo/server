package com.umc.TheGoods.domain.images;

import com.umc.TheGoods.domain.community.Notice;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notice_img")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;
}
