package com.sparta.springfirsthomework.service;

import com.sparta.springfirsthomework.domain.model.Memo;
import com.sparta.springfirsthomework.domain.model.Reply;
import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long createReply(String contents, UserNormal userNormal, Memo memo) {
        isValidateReply(contents);
        Reply reply = new Reply(contents, userNormal, memo);
        replyRepository.save(reply);
        return reply.getId();
    }

    @Transactional
    public Long update(Long id, String contents) {
        isValidateReply(contents);
        Reply reply = replyRepository.findById(id).get();
        reply.update(contents);
        return reply.getId();
    }

    public List<Reply> findAllReplyByMemo(Memo memo) {
        return replyRepository.findAllByMemoLikeOrderByModifiedAtDesc(memo);
    }

    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }

    public Reply findOne(Long id) {
        return replyRepository.findById(id).get();
    }

    private void isValidateReply(String contents) {
        if (contents == "") {
            throw new IllegalArgumentException("내용을 입력해 주세요");
        }
    }
}
