package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.mypage.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByMember_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Address a SET a.addressName = :addressName, a.addressSpec = :addressSpec, a.deliveryMemo = :deliveryMemo, a.zipcode = :zipcode WHERE a.id = :addressId")
    void changeAddress(Long addressId, String addressName, String addressSpec,String deliveryMemo,String zipcode);
}
