package com.umc.thegoods.domain.community;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.enums.InquiryStatus;
import com.umc.thegoods.domain.item.Item;
import com.umc.thegoods.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 로직 규정
public class Inquiry extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //@OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    //private List<Inquiry> inquiryList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private InquiryStatus status;

    @Column(name = "q_message", columnDefinition = "TEXT")
    private String qMessage;

    @Column(columnDefinition = "TEXT")
    private String qImage;

    @Column(name = "checked")
    private boolean checked = false;

}
