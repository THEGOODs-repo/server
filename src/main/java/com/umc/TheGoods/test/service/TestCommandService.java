package com.umc.TheGoods.test.service;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.test.TestRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestCommandService {

    Member addMember(TestRequestDTO.setMemberDTO request, MultipartFile multipartFile);

    Item addItem(Member member, TestRequestDTO.setItemDTO request, MultipartFile itemThumbnail, List<MultipartFile> itemImgList);
}
