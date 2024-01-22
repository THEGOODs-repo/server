package com.umc.thegoods.domain.item;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.ItemStatus;
import com.umc.thegoods.domain.images.ItemImg;
import com.umc.thegoods.domain.member.Member;
import com.umc.thegoods.domain.order.OrderDetail;
import com.umc.thegoods.domain.types.DeliveryType;
import com.umc.thegoods.mapping.Dibs;
import com.umc.thegoods.mapping.Tag.ItemTag;
import com.umc.thegoods.mapping.ViewSearch.ItemView;
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
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
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
}
