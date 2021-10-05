package com.sparta.springfirsthomework.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserNormal {

    public UserNormal(String username, String password, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }
    public UserNormal(String username, String password, String email, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private Long kakaoId;

    //    @JsonIgnore
    @OneToMany(mappedBy = "userNormal", cascade = CascadeType.ALL)
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "userNormal", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
}
