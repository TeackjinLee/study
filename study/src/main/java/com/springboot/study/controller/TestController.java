package com.springboot.study.controller;


import com.springboot.study.Vo.TestVo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {

    @RequestMapping(value = "/home")
    public String home() {
        return "index.html";
    }

    @GetMapping(value="/testValue")
    public String home(String value) {
        return value;
    }

    @RequestMapping("/test")
    public ModelAndView test() throws Exception {
        ModelAndView mv = new ModelAndView("test");
        mv.addObject("name", "TaekJin");

        List<String> testList = new ArrayList<String>();
        testList.add("a");
        testList.add("b");
        testList.add("c");

        mv.addObject("list", testList);
        System.out.println("#############mv############");
        System.out.println(mv);
        return mv;
    }

    @RequestMapping("/thymeleafTest")
    public String thymeleafTest(Model model) {
        TestVo testModel = new TestVo("TaekJin", "이택진");
        model.addAttribute("testModel", testModel);
        return "thymeleaf/thymeleafTest";
    }
}
