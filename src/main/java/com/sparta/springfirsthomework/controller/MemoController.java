package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.domain.model.Memo;
import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.dto.ReplyDto;
import com.sparta.springfirsthomework.dto.MemoRequestDto;
import com.sparta.springfirsthomework.security.UserDetailsImpl;
import com.sparta.springfirsthomework.service.MemoService;
import com.sparta.springfirsthomework.service.ReplyService;
import com.sparta.springfirsthomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/memos")
@RequiredArgsConstructor
@Controller
public class MemoController {
    private final MemoService memoService;
    private final UserService userService;
    private final ReplyService replyService;


    @GetMapping
    public String getMemos(Model model) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().minusDays(1);
        List<MemoRequestDto> memos = memoService.findAllMemoWeek(before, current)
                .stream().map(MemoRequestDto::new)
                .collect(Collectors.toList());
        model.addAttribute("memos", memos);
        return "api/memos";
    }


    @GetMapping("/{id}")
    public String memoView(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("loginUser", userDetails.getUsername());
        } else {
            model.addAttribute("loginUser", "null");
        }

        Memo findOneMemo = memoService.findOne(id);
        MemoRequestDto memoRequestDto = new MemoRequestDto(findOneMemo);
        List<ReplyDto> replies = replyService.findAllReplyByMemo(findOneMemo)
                .stream().map(ReplyDto::new)
                .collect(Collectors.toList());

        model.addAttribute("memo",memoRequestDto);
        model.addAttribute("replies", replies);
        model.addAttribute("createdAt", findOneMemo.getCreatedAt());

        return "api/memo";
    }

    @GetMapping("/newMemo")
    public String newMemoForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return "redirect:/user/login";
        } else return "api/newMemoForm";
    }

    @PostMapping("/newMemo")
    public String newMemo(@RequestParam String title,
                          @RequestParam String contents,
                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                          RedirectAttributes redirectAttributes) {

        UserNormal userNormal = userService.findByUsername(userDetails.getUsername());
        Long savedMemoId = memoService.createMemo(title, userNormal, contents);
        redirectAttributes.addAttribute("id", savedMemoId);

        return "redirect:/api/memos/{id}";
    }

    @GetMapping("/{memoid}/edit")
    public String editForm(@PathVariable Long memoid,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) {
        Memo findOneMemo = memoService.findOne(memoid);
        if (memoService.findOne(memoid).getUserNormal().getId() == userDetails.userNormal.getId()) {
            MemoRequestDto memoRequestDto = new MemoRequestDto(findOneMemo);
            model.addAttribute("memo", memoRequestDto);
            return "api/editForm";
        } else return "redirect:/api/memos/{memoid}"; // redirect attribute로 알람 띄우기
    }

    @PostMapping("/{memoid}/edit")
    public String edit(@PathVariable Long memoid, @ModelAttribute Memo memo) {
        MemoRequestDto memoRequestDto = new MemoRequestDto(memo);
        memoService.update(memoid, memoRequestDto);
        return "redirect:/api/memos/{memoid}";
    }

    @GetMapping("/{memoid}/delete")
    public String deleteMemo(@PathVariable Long memoid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (memoService.findOne(memoid).getUserNormal().getId() == userDetails.userNormal.getId()){
            memoService.deleteById(memoid);
            return "redirect:/api/memos";
        } else return "redirect:/api/memos/{memoid}";


    }



}
