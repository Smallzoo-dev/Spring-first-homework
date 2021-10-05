package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.domain.model.Reply;
import com.sparta.springfirsthomework.dto.ReplyDto;
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

@RequiredArgsConstructor
@RequestMapping("reply")
@Controller
public class ReplyController {
    private final MemoService memoService;
    private final UserService userService;
    private final ReplyService replyService;

    @PostMapping("/{memoid}/newreply")
    public String newReply(@RequestParam String contents,
                           @PathVariable Long memoid,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           RedirectAttributes redirectAttributes) {

        replyService.createReply(contents,
                userService.findByUsername(userDetails.getUsername()),
                memoService.findOne(memoid));
        redirectAttributes.addAttribute("id", memoid);
        return "redirect:/api/memos/{id}";
    }

    @GetMapping("/{memoid}/{replyid}/edit")
    public String editForm(@PathVariable Long memoid,
                           @PathVariable Long replyid,
                           Model model) {
        ReplyDto replyDto = new ReplyDto(replyService.findOne(replyid));
        model.addAttribute("reply", replyDto);
        model.addAttribute("memoid", memoid);
        return "api/editReplyForm";

    }



    @PostMapping("/{memoid}/{replyid}/edit")
    public String editReply(@PathVariable Long memoid,
                            @PathVariable Long replyid,
                            @RequestParam String contents,
                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                            RedirectAttributes redirectAttributes) {
        if (replyService.findOne(replyid).getUserNormal().getId() == userDetails.userNormal.getId()) {
            replyService.update(replyid, contents);
        }
        redirectAttributes.addAttribute("id", memoid);
        return "redirect:/api/memos/{id}";
    }

    @GetMapping("/{memoid}/{replyid}/delete")
    public String deleteReply(@PathVariable Long replyid,
                              @PathVariable Long memoid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              RedirectAttributes redirectAttributes) {
        if (replyService.findOne(replyid).getUserNormal().getId() == userDetails.userNormal.getId()) {
            replyService.deleteById(replyid);
        }
        redirectAttributes.addAttribute("id", memoid);
        return "redirect:/api/memos/{id}";

    }
}
