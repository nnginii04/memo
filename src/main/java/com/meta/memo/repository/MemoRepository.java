package com.meta.memo.repository;

import com.meta.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByCreatedAtDesc();
    List<Memo> findAllByContentsContainingOrderByModifiedAtDesc(String keyword);
    List<Memo> findAllByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);

}