package com.springboot.study.test.controller;

import com.springboot.study.test.service.TestService;
import com.springboot.study.test.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("test");
        mav.addObject("name", "LeeTaekJin");
        List<TestVo> testList = testService.selectTest();
        mav.addObject("list", testList);
        System.out.println(mav);
        return mav;
    }
}
