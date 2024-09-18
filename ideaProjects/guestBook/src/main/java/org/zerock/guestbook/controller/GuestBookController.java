package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestBookDTO;
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

    @GetMapping("/register")
    public void register() {
        log.info("register...");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO guestBookDTO, RedirectAttributes redirectAttributes) {

        log.info("dto... " + guestBookDTO);

        // 새로 추가된 엔티티의 번호
        Long gno = guestBookService.register(guestBookDTO);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("gno: " + gno);

        GuestBookDTO dto = guestBookService.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("remove... " + gno);
        guestBookService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO guestBookDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes, Model model) {
        log.info("post modify..........................");
        log.info("guestBookDTO : " + guestBookDTO);

        guestBookService.modify(guestBookDTO);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", guestBookDTO.getGno());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }

}
