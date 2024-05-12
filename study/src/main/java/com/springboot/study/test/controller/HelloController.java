package com.springboot.study.test.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String test() {
        return "Hello, world!";
    }

//    @RequestMapping("/test")
//    public ModelAndView test2(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("test");
////        mav.addObject("name", "LeeTaekJin");
//        return mav;
//    }
}
