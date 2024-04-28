package com.springboot.study.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
public class TestController {
    @RequestMapping(value = "/home")
    public String home() {
        return "index.html";
    }
}
