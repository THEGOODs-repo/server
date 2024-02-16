package com.umc.TheGoods.test;

import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.domain.enums.ItemStatus;
import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.images.ItemImg;
import com.umc.TheGoods.domain.images.ProfileImg;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.mapping.Tag.CategoryTag;
import com.umc.TheGoods.domain.mapping.Tag.ItemTag;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.types.DeliveryType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestConverter {

    public static Member toTestMember(TestRequestDTO.setMemberDTO request, BCryptPasswordEncoder encoder) {
        Date date = new Date();

        return Member.builder()
                .nickname(request.getNickname())
                .password(encoder.encode("12345678"))
                .email(request.getUuid() + "@gmail.com")
                .birthday(date)
                .gender(Gender.FEMALE)
                .phone("01012345678")
                .memberRole(MemberRole.SELLER)
                .memberCategoryList(new ArrayList<>())
                .memberTermList(new ArrayList<>())
                .itemList(new ArrayList<>())
                .build();

    }

    public static ProfileImg toProfileImg(String imgUrl) {
        return ProfileImg.builder()
                .url(imgUrl)
                .build();
    }

    public static Item toTestItem(TestRequestDTO.setItemDTO request) {

        DeliveryType deliveryType = null;

        switch (request.getDeliveryType()) {
            case 1:
                deliveryType = DeliveryType.PO;
                break;
            case 2:
                deliveryType = DeliveryType.CJ;
                break;
            case 3:
                deliveryType = DeliveryType.LOTTE;
                break;
            case 4:
                deliveryType = DeliveryType.LOGEN;
                break;
            case 5:
                deliveryType = DeliveryType.HANJIN;
                break;
            case 6:
                deliveryType = DeliveryType.GS25;
                break;
            case 7:
                deliveryType = DeliveryType.CU;
                break;
            case 8:
                deliveryType = DeliveryType.ETC;
                break;
        }

        return Item.builder()
                .name(request.getName())
                .deliveryType(deliveryType)
                .description(request.getDescription())
                .deliveryFee(request.getDeliveryFee())
                .deliveryDate(request.getDeliveryDate())
                .isLimitless(request.getIsLimitless())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dibsCount(request.dibsCount)
                .viewCount(request.viewCount)
                .salesCount(request.salesCount)
                .tagsCount(0)
                .status(ItemStatus.ONSALE)
                .price(request.getPrice())
                .stock(request.getStock())
                .itemTagList(new ArrayList<>())
                .itemImgList(new ArrayList<>())
                .build();
    }

    public static ItemImg toItemImg(String imgUrl, boolean isThumbnail) {
        return ItemImg.builder()
                .thumbnail(isThumbnail)
                .url(imgUrl)
                .build();
    }

    public static Tag toTag(String name) {
        return Tag.builder()
                .name(name)
                .categoryTagList(new ArrayList<>())
                .tagSearchList(new ArrayList<>())
                .itemTagList(new ArrayList<>())
                .build();
    }

    public static List<CategoryTag> toCategoryTagList(List<Tag> tagList) {
        return tagList.stream().map(tag -> {
            return CategoryTag.builder().build();
        }).collect(Collectors.toList());
    }

    public static CategoryTag toCategoryTag() {
        return CategoryTag.builder().build();
    }

    public static TestResponseDTO.addItemDTO toAddItemDTO(Item item) {
        return TestResponseDTO.addItemDTO.builder()
                .itemId(item.getId())
                .createdAt(item.getCreatedAt())
                .build();
    }

    public static ItemTag toItemTag() {
        return ItemTag.builder().build();
    }

}
