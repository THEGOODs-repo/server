package com.umc.thegoods.domain;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.BoardStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class Board extends BaseDateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private BoardStatus status;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Users user;

//    @JoinColumn(name = "item_id")
//    private Item item;
}
