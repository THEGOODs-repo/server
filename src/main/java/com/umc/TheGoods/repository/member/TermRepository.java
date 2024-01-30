package com.umc.TheGoods.repository.member;

import com.umc.TheGoods.domain.member.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {

    Optional<Term> findById(Long id);
}
