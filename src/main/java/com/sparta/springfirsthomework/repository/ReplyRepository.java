package com.sparta.springfirsthomework.repository;


import com.sparta.springfirsthomework.domain.model.Memo;
import com.sparta.springfirsthomework.domain.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByMemoLikeOrderByModifiedAtDesc(Memo memo);
}
