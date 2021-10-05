package com.sparta.springfirsthomework.domain.model;


import com.sparta.springfirsthomework.dto.MemoRequestDto;
import com.sparta.springfirsthomework.utils.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Memo extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserNormal userNormal;

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY)
    private List<Reply> replies;

//    public Memo newMemo(String title, String contents, String username) {
//        Memo memo = new Memo();
//        memo.setTitle();
//
//        return memo;
//    }
//

    public Memo(String title, UserNormal userNormal, String contents) {

        this.title = title;
        this.userNormal = userNormal;
        this.contents = contents;
    }

    public void update(MemoRequestDto requestDto) {
    }
}

