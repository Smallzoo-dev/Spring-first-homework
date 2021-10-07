package com.sparta.springfirsthomework.service;

import com.sparta.springfirsthomework.domain.model.Memo;
import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.repository.MemoRepository;
import com.sparta.springfirsthomework.dto.MemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemoService {
    private final MemoRepository memoRepository;

    @Transactional
    public Long createMemo(String title, UserNormal userNormal, String contents) {
        validateMemo(contents, title);
        Memo memo = new Memo(title, userNormal, contents);
        memoRepository.save(memo);
        return memo.getId();
    }


    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        validateMemo(requestDto.getContents(), requestDto.getTitle());

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id값이 존재하지 않습니다.")
        );
        memo.update(requestDto);
        return memo.getId();
    }


    public List<Memo> findAllMemoWeek(LocalDateTime before, LocalDateTime current) {
        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(before, current);
    }

    public Memo findOne(Long id) {
        return memoRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        memoRepository.deleteById(id);
    }

    private void validateMemo(String contents, String title) {
        if (contents == "" || title == "") {
            throw new IllegalArgumentException("제목, 내용을 빠짐없이 입력해 주세요.");
        }
    }
}
