package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.enums.MemberRole;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhone(String phone);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.memberRole = :newRole WHERE m.id = :memberId")
    void changeMemberRole(Long memberId, MemberRole newRole);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.phone = :phone WHERE m.id = :memberId")
    void changeMemberPhone(Long memberId, String phone);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.name = :name WHERE m.id = :memberId")
    void changeMemberName(Long memberId, String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.itemNotice = false WHERE m.id = :memberId")
    void changeItemNotificationTrue(Long memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.itemNotice = true WHERE m.id = :memberId")
    void changeItemNotificationFalse(Long memberId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.messageNotice = false WHERE m.id = :memberId")
    void changeMessageNotificationTrue(Long memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.messageNotice = true WHERE m.id = :memberId")
    void changeMessageNotificationFalse(Long memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.marketingNotice = false WHERE m.id = :memberId")
    void changeMarketingNotificationTrue(Long memberId);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.marketingNotice = true WHERE m.id = :memberId")
    void changeMarketingNotificationFalse(Long memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.postNotice = true WHERE m.id = :memberId")
    void changePostNotificationFalse(Long memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.postNotice = false WHERE m.id = :memberId")
    void changePostNotificationTrue(Long memberId);



}
