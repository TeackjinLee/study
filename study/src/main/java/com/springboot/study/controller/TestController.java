package com.springboot.study.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
}
