package com.umc.thegoods.domain.images;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.item.Review;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewImg extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
