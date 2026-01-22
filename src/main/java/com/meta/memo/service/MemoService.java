package com.meta.memo.service;

import com.meta.memo.domain.Memo;
import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class MemoService {

    // 멤버 변수 선언
    private final MemoRepository memoRepository;

    // 생성자 주입(DI)
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    // 메모 생성
    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        Memo newMemo = new Memo(memoRequestDto);
        Memo savedMemo = memoRepository.save(newMemo);
        return new MemoResponseDto(savedMemo);
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        return memoRepository.findAllByContentsContainingOrderByModifiedAtDesc(keyword)
                .stream()
                .map(MemoResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemosByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return memoRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(start, end)
                .stream()
                .map(MemoResponseDto::new)
                .toList();
    }


    // 메모 전체 조회 (생성시간 최신순 정렬)
    public List<MemoResponseDto> getMemos() {
        return memoRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(MemoResponseDto::new)
                .toList();
    }

    // 메모 단건 조회
    public Memo getMemoById(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다."));
    }

    // 메모 수정
    @Transactional
    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        Memo foundMemo = getMemoById(id);
        foundMemo.update(memoRequestDto);
        return id;
    }

    // 메모 삭제
    @Transactional
    public Long deleteMemo(Long id) {
        Memo foundMemo = getMemoById(id);
        memoRepository.delete(foundMemo);
        return id;
    }
}
