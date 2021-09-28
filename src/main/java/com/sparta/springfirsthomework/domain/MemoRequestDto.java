package com.sparta.springfirsthomework.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class MemoRequestDto {
    private Long id;
    private String username;
    private String title;
    private String contents;

    public MemoRequestDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.title = memo.getTitle();
        this.contents = memo.getContents();

    }
}
