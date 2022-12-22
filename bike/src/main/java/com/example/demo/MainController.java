package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    
    @RequestMapping("/test")
    @ResponseBody
    public String text() {
        return "안녕하세요 테스트 화면입니다.";
    }

    @RequestMapping("/")
    public String main() {
        return "main_form";
    }

    @RequestMapping("/main_login")
    public String main_login() {
        return "main_login";
    }

}
