package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.domain.Memo;
import com.sparta.springfirsthomework.domain.MemoRepository;
import com.sparta.springfirsthomework.domain.MemoRequestDto;
import com.sparta.springfirsthomework.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/memos")
@RequiredArgsConstructor
@Controller
public class MemoController {
    private final MemoRepository memoRepository;
//    private final MemoTestRepository memoTestRepository;
    private final MemoService memoService;


    @GetMapping
    public String getMemos(Model model) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().minusDays(1);
        List<Memo> memos = memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(before, current);

//        List<Memo> memos = memoTestRepository.findAll();
        model.addAttribute("memos", memos);
        return "api/memos";
    }

    @GetMapping("/{id}")
    public String memoView(@PathVariable long id, Model model) {
        Optional<Memo> byId = memoRepository.findById(id);
        MemoRequestDto memoRequestDto = new MemoRequestDto(byId.get());
        model.addAttribute("memo",memoRequestDto);
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
    public String newMemo(Memo memo, RedirectAttributes redirectAttributes) {
        memoRepository.save(memo);
//        Memo save = memoTestRepository.save(memo);
        redirectAttributes.addAttribute("id", memo.getId());
//        redirectAttributes.addAttribute("id", memo.getId());
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
