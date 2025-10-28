package com.startxai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/apitest")
    public String testApi() {
        return "✅ StartXAIChatBot backend connected successfully!";
    }
}
