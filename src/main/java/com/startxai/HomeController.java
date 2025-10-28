package com.startxai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 StartXAIChatBot Backend is Running Successfully!";
    }

    @GetMapping("/test")
    public String test() {
        return "✅ Backend Test Endpoint Working!";
    }
}
