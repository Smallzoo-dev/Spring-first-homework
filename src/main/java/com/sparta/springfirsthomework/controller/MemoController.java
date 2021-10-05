package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.domain.model.Memo;
import com.sparta.springfirsthomework.domain.model.Reply;
import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.dto.ReplyDto;
import com.sparta.springfirsthomework.repository.MemoRepository;
import com.sparta.springfirsthomework.dto.MemoRequestDto;
import com.sparta.springfirsthomework.repository.ReplyRepository;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import com.sparta.springfirsthomework.security.UserDetailsImpl;
import com.sparta.springfirsthomework.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/memos")
@RequiredArgsConstructor
@Controller
public class MemoController {
    private final MemoRepository memoRepository;
    private final MemoService memoService;
    private final ReplyRepository replyRepository;
    private final UserNormalRepository userNormalRepository;


    @GetMapping
    public String getMemos(Model model) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().minusDays(1);
        List<MemoRequestDto> memos = memoRepository
                .findAllByModifiedAtBetweenOrderByModifiedAtDesc(before, current)
                .stream().map(MemoRequestDto::new)
                .collect(Collectors.toList());

        model.addAttribute("memos", memos);
        return "api/memos";
    }


    @GetMapping("/{id}")
    public String memoView(@PathVariable long id, Model model) {
        Optional<Memo> byId = memoRepository.findById(id);
        MemoRequestDto memoRequestDto = new MemoRequestDto(byId.get());
        List<ReplyDto> replies = replyRepository.findAllByMemoLikeOrderByModifiedAtDesc(byId.get())
                .stream().map(ReplyDto::new)
                .collect(Collectors.toList());

        model.addAttribute("memo",memoRequestDto);
        model.addAttribute("replies", replies);
        model.addAttribute("createdAt", byId.get().getCreatedAt());

//        Memo findmemo = memoTestRepository.findById(id);
//        model.addAttribute("memo", findmemo);
        return "api/memo";

    }

    @GetMapping("/newMemo")
    public String newMemoForm() {
        return "api/newMemoForm";
    }

    @PostMapping("/newMemo")
    public String newMemo(@RequestParam String title,
                          @RequestParam String contents,
                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                          RedirectAttributes redirectAttributes) {

        Optional<UserNormal> userNormal = userNormalRepository.findByUsername(userDetails.getUsername());
        Memo memo = new Memo(title, userNormal.get(), contents);
        memoRepository.save(memo);

        redirectAttributes.addAttribute("id", memo.getId());

        return "redirect:/api/memos/{id}";
    }

    @GetMapping("/{memoid}/edit")
    public String editForm(@PathVariable Long memoid, Model model) {
        Optional<Memo> byId = memoRepository.findById(memoid);
        MemoRequestDto memoRequestDto = new MemoRequestDto(byId.get());
        model.addAttribute("memo", memoRequestDto);
//        Memo foundMemo = memoTestRepository.findById(id);

        return "api/editForm";
    }

    @PostMapping("/{memoid}/edit")
    public String edit(@PathVariable Long memoid, @ModelAttribute Memo memo) {
        MemoRequestDto memoRequestDto = new MemoRequestDto(memo);
        memoService.update(memoid, memoRequestDto);

//        memoTestRepository.update(id, memo);
        return "redirect:/api/memos/{memoid}";
    }

    @GetMapping("/{memoid}/delete")
    public String deleteMemo(@PathVariable Long memoid) {
        memoRepository.deleteById(memoid);
        return "redirect:/api/memos";
    }



}
