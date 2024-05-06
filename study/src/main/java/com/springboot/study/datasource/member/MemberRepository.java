package com.springboot.study.datasource.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberDto, Long> {
    Optional<MemberDto> findByEmail(String email);

    boolean existsByEmail(String email);
}