package com.springboot.study.test.controller;

import com.springboot.study.api.common.config.auth.dto.SessionUser;
import com.springboot.study.test.service.TestService;
import com.springboot.study.test.vo.TestVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final HttpSession httpSession;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public ModelAndView index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        System.out.println("user#####################");
        System.out.println(user);
//        if (user != null) {
//            model.addAttribute("userName", user.getName());
//        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("userName", user.getName());
        mav.addObject("picture", user.getPicture());

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("name", "LeeTaekJin");
//        List<TestVo> testList = testService.selectTest();
//        mav.addObject("list", testList);
        logger.trace("TRACE Level 테스트");
        logger.debug("DEBUG Level 테스트");
        logger.info("INFO Level 테스트");
        logger.warn("WARN Level 테스트");
        logger.error("ERROR Level 테스트");
        return mav;
    }
}
