package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    
    @RequestMapping("/")
    public String main() {
        return "main_form";
    }
    @RequestMapping("/main_login")
    public String main_login() {
        return "main_login";
    }
}
