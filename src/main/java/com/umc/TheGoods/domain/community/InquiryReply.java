package com.umc.TheGoods.domain.community;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inquiry_reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class InquiryReply extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @Column(columnDefinition = "TEXT")
    private String rMessage;

    @Column(columnDefinition = "TEXT")
    private String rImage;

    @Column(name = "checked")
    private boolean checked = false;
}
