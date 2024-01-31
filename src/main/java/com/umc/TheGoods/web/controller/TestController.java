package com.umc.TheGoods.web.controller;

import com.umc.TheGoods.domain.enums.Gender;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.service.ItemService.ItemCommandService;
import com.umc.TheGoods.service.MemberService.MemberCommandServiceImpl;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.web.dto.item.ItemRequestDTO;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class TestController {

    private final MemberCommandServiceImpl memberCommandService;
    private final MemberQueryService memberQueryService;
    private final ItemCommandService itemCommandService;


    @GetMapping("/test/hello")
    @Operation(summary = "Return hello", description = "simple API for swagger test!")
    public String hello() {
        return "Hello, Swagger!";
    }

    @GetMapping("/setTestData")
    @Operation(summary = "테스트용 데이터 생성 API", description = "회원, 상품, 상품 옵션 dummy data를 생성해 db에 저장합니다. category, term, tag 데이터는 db에 수동으로 입력해야 합니다. (id가 1인 category, term, tag 데이터가 필요합니다.) \n\n " +
            "category 생성 sql: INSERT INTO category(name,created_at,updated_at) VALUES ('아이돌',now(),now());\n\n" +
            "tag 생성 sql: INSERT INTO tag(name,created_at,updated_at) VALUES ('포카',now(),now());\n\n" +
            "term 생성 sql: INSERT INTO term(title,created_at,updated_at) VALUES ('정보 이용 동의',now(),now());\n\n" +
            "생성된 회원 계정은 아래와 같습니다.\n\n" +
            "1. 구매자 계정 - email: testbuyer@gmail.com, password: 12345678\n\n" +
            "2. 판매자 계정 - email: testseller@gmail.com, password: 12345678\n\n" +
            "3. 비로그인 주문용 계정 - email: nologinuser@gmail.com, password:12345678")
    public String setTestData() {

        // 회원 가입
        Date date = new Date();
        List<Boolean> termAgreeList = new ArrayList<>();
        termAgreeList.add(true);
        List<Long> memberCategoryList = new ArrayList<>();
        memberCategoryList.add(1L);

        MemberRequestDTO.JoinDTO joinBuyerDTO = new MemberRequestDTO.JoinDTO("test_buyer", "12345678", "testbuyer@gmail.com", date, "01012345678", Gender.MALE, termAgreeList, memberCategoryList);
        MemberRequestDTO.JoinDTO joinSellerDTO = new MemberRequestDTO.JoinDTO("test_seller", "12345678", "testseller@gmail.com", date, "01012341234", Gender.MALE, termAgreeList, memberCategoryList);
        MemberRequestDTO.JoinDTO noLoginUserDTO = new MemberRequestDTO.JoinDTO("no_login_user", "12345678", "nologinuser@gmail.com", date, "01087654321", Gender.MALE, termAgreeList, memberCategoryList);


        memberCommandService.join(joinBuyerDTO);
        memberCommandService.join(joinSellerDTO);
        memberCommandService.join(noLoginUserDTO);

        // item 및 itemOption 등록
        Member member = memberQueryService.findMemberById(2L).get();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2024, 3, 1);

        ItemRequestDTO.itemImgDTO itemImgDTO = new ItemRequestDTO.itemImgDTO(false, "img url");
        List<ItemRequestDTO.itemImgDTO> itemImgDTOList = new ArrayList<>();
        itemImgDTOList.add(itemImgDTO);

        List<Long> tagList = new ArrayList<>();
        tagList.add(1L);

        // 뉴진스 포카 item 등록
        ItemRequestDTO.itemOptionDTO itemOptionDTO1 = new ItemRequestDTO.itemOptionDTO("해린 포카", 1000L, 20);
        ItemRequestDTO.itemOptionDTO itemOptionDTO2 = new ItemRequestDTO.itemOptionDTO("민지 포카", 2000L, 20);
        ItemRequestDTO.itemOptionDTO itemOptionDTO3 = new ItemRequestDTO.itemOptionDTO("하니 포카", 3000L, 20);
        List<ItemRequestDTO.itemOptionDTO> itemOptionDTOList1 = new ArrayList<>();
        itemOptionDTOList1.add(itemOptionDTO1);
        itemOptionDTOList1.add(itemOptionDTO2);
        itemOptionDTOList1.add(itemOptionDTO3);

        ItemRequestDTO.UploadItemDTO uploadItemDTO1 = new ItemRequestDTO.UploadItemDTO("뉴진스 포카", "ONSALE", null, null, 3000, 1, 3, "뉴진스 포카입니다.", false, startDate, endDate, 0L, 0L, 0L, 1L, itemImgDTOList, itemOptionDTOList1, tagList);
        itemCommandService.uploadItem(member, uploadItemDTO1);

        // 아이브 포카 item 등록
        ItemRequestDTO.itemOptionDTO itemOptionDTO4 = new ItemRequestDTO.itemOptionDTO("원영 포카", 1000L, 20);
        ItemRequestDTO.itemOptionDTO itemOptionDTO5 = new ItemRequestDTO.itemOptionDTO("유진 포카", 2000L, 20);
        List<ItemRequestDTO.itemOptionDTO> itemOptionDTOList2 = new ArrayList<>();
        itemOptionDTOList2.add(itemOptionDTO4);
        itemOptionDTOList2.add(itemOptionDTO5);

        ItemRequestDTO.UploadItemDTO uploadItemDTO2 = new ItemRequestDTO.UploadItemDTO("아이브 포카", "ONSALE", null, null, 2000, 1, 3, "아이브 포카입니다.", false, startDate, endDate, 0L, 0L, 0L, 1L, itemImgDTOList, itemOptionDTOList2, tagList);
        itemCommandService.uploadItem(member, uploadItemDTO2);

        // 카리나 포카 item 등록 (단일 상품)
        ItemRequestDTO.UploadItemDTO uploadItemDTO3 = new ItemRequestDTO.UploadItemDTO("카리나 포카", "ONSALE", 100, 3000L, 3000, 1, 3, "카리나 포카입니다.", false, startDate, endDate, 0L, 0L, 0L, 1L, itemImgDTOList, new ArrayList<>(), tagList);
        itemCommandService.uploadItem(member, uploadItemDTO3);


        return "Setting Test Data";
    }
}
