package com.umc.TheGoods.domain.item;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.community.Inquiry;
import com.umc.TheGoods.domain.enums.ItemStatus;
import com.umc.TheGoods.domain.images.ItemImg;
import com.umc.TheGoods.domain.mapping.Dibs;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.OrderDetail;
import com.umc.TheGoods.domain.types.DeliveryType;
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
public class Item extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private ItemStatus status;

    @Column(nullable = false, length = 6)
    private Integer stock;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 6)
    private Integer deliveryFee;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private DeliveryType deliveryType;

    @Column(nullable = false, length = 2)
    private Integer delivery_date;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isLimitless;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Long dibsCount;

    @Column(nullable = false)
    private Long salesCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImg> itemImgList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemOption> itemOptionList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemTag> itemTagList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemView> itemViewList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Dibs> dibsList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList = new ArrayList<>();
}
