package com.sparta.springfirsthomework.service;

import com.sparta.springfirsthomework.domain.Memo;
import com.sparta.springfirsthomework.domain.MemoRepository;
import com.sparta.springfirsthomework.domain.MemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemoService {
    private final MemoRepository memoRepository;

    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id값이 존재하지 않습니다.")
        );
        memo.update(requestDto);
        return memo.getId();
    }
}
