package com.sparta.springfirsthomework.dto;

import com.sparta.springfirsthomework.domain.model.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReplyDto {
    private String contents;
    private String username;
    private String createdAt;

    public ReplyDto(Reply reply) {
        this.contents = reply.getContents();
        this.username = reply.getUserNormal().getUsername();
        this.createdAt = reply.getCreatedAt().toString();
    }
}
