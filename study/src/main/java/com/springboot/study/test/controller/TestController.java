package com.springboot.study.test.controller;

import com.springboot.study.test.service.TestService;
import com.springboot.study.test.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestService testService;

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("test");
        mav.addObject("name", "LeeTaekJin");
        List<TestVo> testList = testService.selectTest();
        mav.addObject("list", testList);
        logger.trace("TRACE Level 테스트");
        logger.debug("DEBUG Level 테스트");
        logger.info("INFO Level 테스트");
        logger.warn("WARN Level 테스트");
        logger.error("ERROR Level 테스트");
        return mav;
    }
}
