package com.umc.thegoods.domain.community;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class Comment extends BaseDateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    /**
     * TODO
     * 대댓글 관련 parrentId 처리 필요
     * admin 엔티티 처리(회원 도메인과 상의)
     */
    private Long parrentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
