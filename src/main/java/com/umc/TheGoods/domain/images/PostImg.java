package com.umc.TheGoods.domain.images;

import com.umc.TheGoods.domain.community.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_img")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;
}
