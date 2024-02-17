package com.umc.TheGoods.repository.item;

import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.Tag;
import com.umc.TheGoods.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByMember(Member member, PageRequest pageRequest);

    Page<Item> findAllByNameContaining(String name, PageRequest pageRequest);

    Page<Item> findAllByCategoryName(String name, PageRequest pageRequest);

    List<Item> findAllByItemTagListTagIn(List<Tag> tags);

    //Page<Item> findAllByItemTagListIn(List<ItemTag> itemTagList, PageRequest pageRequest);

    Page<Item> findAllByEndDateGreaterThanEqual(LocalDate localDate, PageRequest pageRequest);

    Page<Item> findAllByStartDateLessThanEqual(LocalDate localDate, PageRequest pageRequest);

    @Query("SELECT i FROM Item i LEFT JOIN i.reviewList r WHERE i.category.name = :categoryName GROUP BY i.id ORDER BY COUNT(r) DESC")
    Page<Item> findAllByCategoryNameOrderByReviewListSizeDesc(@Param("categoryName") String categoryName, PageRequest pageRequest);

    @Query("SELECT i FROM Item i LEFT JOIN i.reviewList r WHERE i.name LIKE %:itemName% GROUP BY i.id ORDER BY COUNT(r) DESC")
    Page<Item> findAllByItemNameContainingOrdOrderByReviewListSizeDesc(@Param("itemName") String itemName, PageRequest pageRequest);

    @Query("SELECT i FROM Item i LEFT JOIN i.reviewList r WHERE i.member.nickname = :memberName GROUP BY i.id ORDER BY COUNT(r) DESC")
    Page<Item> findAllBySellerNameOrderByReviewListSizeDesc(@Param("memberName") String memberName, PageRequest pageRequest);

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN i.itemTagList itl " +
            "LEFT JOIN i.reviewList r " +
            "WHERE EXISTS (" +
            "   SELECT 1 FROM ItemTag it " +
            "   WHERE it.tag IN :tags " +
            "   AND it.item.id = i.id" +
            ") " +
            "GROUP BY i.id " +
            "ORDER BY COUNT(r) DESC")
    List<Item> findAllByItemTagListTagInOrderByReviewListSizeDesc(@Param("tags") List<Tag> tags);

    Page<Item> findAllByIdIn(List<Long> itemId, PageRequest pageRequest);
}
