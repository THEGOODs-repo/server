package com.umc.thegoods.domain.member;

import com.umc.thegoods.domain.Payment;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.community.Comment;
import com.umc.thegoods.domain.community.Inquiry;
import com.umc.thegoods.domain.community.Notice;
import com.umc.thegoods.domain.community.Post;
import com.umc.thegoods.domain.enums.Gender;
import com.umc.thegoods.domain.enums.MemberRole;
import com.umc.thegoods.domain.enums.MemberStatus;
import com.umc.thegoods.domain.item.Item;
import com.umc.thegoods.domain.item.Review;
import com.umc.thegoods.domain.mapping.Dibs;
import com.umc.thegoods.domain.mapping.ViewSearch.ItemView;
import com.umc.thegoods.domain.mapping.ViewSearch.TagSearch;
import com.umc.thegoods.domain.mapping.comment.CommentLike;
import com.umc.thegoods.domain.mapping.comment.CommentMention;
import com.umc.thegoods.domain.mapping.member.MemberCategory;
import com.umc.thegoods.domain.mapping.member.MemberTerm;
import com.umc.thegoods.domain.mapping.post.PostLike;
import com.umc.thegoods.domain.mypage.*;
import com.umc.thegoods.domain.order.Cart;
import com.umc.thegoods.domain.order.Orders;
import com.umc.thegoods.domain.types.SocialType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private MemberRole memberRole;

    @Column(columnDefinition = "VARCHAR(15)")
    private String nickname;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String email;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(columnDefinition = "VARCHAR(13)")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus memberStatus;

    @Column(columnDefinition = "TEXT")
    private String kakaoAuth;

    @Column(columnDefinition = "TEXT")
    private String naverAuth;

    //메세지 관련 수신 동의
    @Column(columnDefinition = "varchar(1) DEFAULT 'X'")
    private String messageNotice;

    //상품 관련 수신 동의
    @Column(columnDefinition = "varchar(1) DEFAULT 'X'")
    private String itemNotice;

    //마케팅 수신 동의
    @Column(columnDefinition = "varchar(1) DEFAULT 'X'")
    private String marketingNotice;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCategory> memberCategoryList = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTerm> memberTermList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> memberAddressList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Account> memberAccountList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Declaration> memberDeclarationList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Survey> memberSurveyList = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private ContactTime contactTime;

    @OneToOne(mappedBy = "member")
    private Revenue revenue;

    @OneToOne(mappedBy = "member")
    private WithdrawReason withdrawReason;

    //notification 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notification = new ArrayList<>();

    // item 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ItemView> itemViewList = new ArrayList<>();

    // dibs 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Dibs> dibsList = new ArrayList<>();

    // review 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    // cart 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

    // orders 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Orders> ordersList = new ArrayList<>();

    // tag_search 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TagSearch> tagSearchList = new ArrayList<>();

    // Post 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    // PostLike 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PostLike> postLikeList = new ArrayList<>();

    // Follow 양방향 매핑(mappedBy = "member"로 변경 할 수도 있음)
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followerList = new ArrayList<>();

    // Notice 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notice> noticeList = new ArrayList<>();

    // Comment 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    // CommentLike 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    // CommentMention 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CommentMention> commentMentionList = new ArrayList<>();

    // Inquiry 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList = new ArrayList<>();

    // Payment 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Payment> paymentList = new ArrayList<>();
}
