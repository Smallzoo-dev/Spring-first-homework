package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.domain.Memo;
import com.sparta.springfirsthomework.domain.MemoRepository;
import com.sparta.springfirsthomework.domain.MemoRequestDto;
import com.sparta.springfirsthomework.domain.MemoTestRepository;
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
    //    private final MemoRepository memoRepository;
    private final MemoTestRepository memoTestRepository;
    private final MemoService memoService;
    LocalDateTime current = LocalDateTime.now();
    LocalDateTime before = LocalDateTime.now().minusDays(1);

    @GetMapping
    public String getMemos(Model model) {
//        List<Memo> memos = memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(current, before);

        List<Memo> memos = memoTestRepository.findAll();
        model.addAttribute("memos", memos);
        return "api/memos";
    }

    @GetMapping("/{id}")
    public String memoView(@PathVariable long id, Model model) {
//        Optional<Memo> byId = memoRepository.findById(id);
//        model.addAttribute("memo", byId.get());

        Memo findmemo = memoTestRepository.findById(id);
        model.addAttribute("memo", findmemo);
        return "api/memo";

    }

    @GetMapping("/newMemo")
    public String newMemoForm() {
        return "api/newMemoForm";
    }

    @PostMapping("/newMemo")
    public String newMemo(Memo memo, RedirectAttributes redirectAttributes) {
//        memoRepository.save(memo);
        Memo save = memoTestRepository.save(memo);
//        redirectAttributes.addAttribute("id", memo.getId());
        redirectAttributes.addAttribute("id", memo.getId());
        return "redirect:/api/memos/{id}";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
//        Optional<Memo> byId = memoRepository.findById(id);
//        model.addAttribute("memo", byId.get());
        Memo foundMemo = memoTestRepository.findById(id);
        model.addAttribute("memo", foundMemo);
        return "api/editForm";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Memo memo) {
//        MemoRequestDto memoRequestDto = new MemoRequestDto(memo);
//        memoService.update(id, memoRequestDto);

        memoTestRepository.update(id, memo);
        return "redirect:/api/memos/{id}";
    }

//    @DeleteMapping("/api/memos/{id}")
//    public String deleteMemo(@PathVariable Long id) {
//        memoRepository.deleteById(id);
//        return "api/memos";
//    }
//
//    @PutMapping("/api/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        memoService.update(id, requestDto);
//        return id;
//    }


}
