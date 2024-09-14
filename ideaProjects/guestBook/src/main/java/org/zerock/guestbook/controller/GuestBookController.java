package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.dto.common.PageRequestDTO;
import org.zerock.guestbook.service.GuestBookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor    // 자동 주입을 위한 Annotation
public class GuestBookController {

    private final GuestBookService guestBookService;

    @GetMapping("/")
    public String list(){

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list........................." + pageRequestDTO);
        model.addAttribute("result", guestBookService.getList(pageRequestDTO));
    }

}
