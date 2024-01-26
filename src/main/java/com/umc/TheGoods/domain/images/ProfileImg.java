package com.umc.TheGoods.domain.images;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_img_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;
}
