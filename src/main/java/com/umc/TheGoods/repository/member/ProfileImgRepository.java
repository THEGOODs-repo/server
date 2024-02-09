package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.images.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

    Optional<ProfileImg> findByMember_Id(Long id);

    void deleteByMember_Id(Long id);
}
