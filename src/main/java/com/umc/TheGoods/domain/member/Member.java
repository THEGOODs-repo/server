package com.umc.TheGoods.domain.member;

import com.umc.TheGoods.domain.common.BaseDateTimeEntity;
import com.umc.TheGoods.domain.community.Comment;
import com.umc.TheGoods.domain.community.Inquiry;
import com.umc.TheGoods.domain.community.Notice;
import com.umc.TheGoods.domain.community.Post;
import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.enums.MemberStatus;
import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Review;
import com.umc.TheGoods.domain.mapping.Dibs;
import com.umc.TheGoods.domain.mapping.ViewSearch.ItemView;
import com.umc.TheGoods.domain.mapping.ViewSearch.TagSearch;
import com.umc.TheGoods.domain.mapping.comment.CommentLike;
import com.umc.TheGoods.domain.mapping.comment.CommentMention;
import com.umc.TheGoods.domain.mapping.member.MemberTerm;
import com.umc.TheGoods.domain.mapping.post.PostLike;
import com.umc.TheGoods.domain.mypage.*;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.domain.order.Orders;
import com.umc.TheGoods.domain.payment.Payment;
import com.umc.TheGoods.domain.types.SocialType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
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

    @Column(columnDefinition = "VARCHAR(50)")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(20)")
    private String name;

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
    @Column(columnDefinition = "VARCHAR(15)")
    @ColumnDefault("'ACTIVE'")
    private MemberStatus memberStatus;

    @Column(columnDefinition = "TEXT")
    private String kakaoAuth;

    @Column(columnDefinition = "TEXT")
    private String naverAuth;

    @Column(columnDefinition = "VARCHAR(160)")
    private String introduce;

    //메세지 관련 수신 동의
    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean messageNotice;

    //상품 관련 수신 동의
    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean itemNotice;

    //마케팅 수신 동의
    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean marketingNotice;

    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean postNotice;

    @Column(columnDefinition = "BOOLEAN")
    @ColumnDefault("false")
    private Boolean infoTerm;

    private LocalDateTime deletedAt;


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
    private ProfileImg profileImg;


    //notification 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notification = new ArrayList<>();

    // item 양방향 매핑
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
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
    @OneToMany(mappedBy = "member", cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
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
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Inquiry> incommingInquiryList = new ArrayList<>();

    // Payment 양방향 매핑
    @OneToMany(mappedBy = "member", cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Payment> paymentList = new ArrayList<>();

    public void setProfileImg(ProfileImg profileImg) {
        this.profileImg = profileImg;
    }

    public void setMemberStatus(MemberStatus memberStatus, LocalDateTime time){
        this.memberStatus = memberStatus;
        this.deletedAt = time;
    }
}
