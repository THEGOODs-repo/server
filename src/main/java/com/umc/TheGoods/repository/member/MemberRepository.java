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


}
