package com.sparta.springfirsthomework.dto;

import com.sparta.springfirsthomework.domain.model.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemoRequestDto {
    private Long id;
    private String username;
    private String title;
    private String contents;

    public MemoRequestDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUserNormal().getUsername();
        this.title = memo.getTitle();
        this.contents = memo.getContents();

    }
}
