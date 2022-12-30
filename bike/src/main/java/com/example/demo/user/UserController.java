package com.example.demo.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
            "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        
        try {
            userService.create(userCreateForm.getNickname(),
            userCreateForm.getUsername(), 
            userCreateForm.getEmail(), 
            userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("singupFailed","이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("singupFailed", e.getMessage());
            return "signup_form";
        }
        
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/rental_office")
    public String rental_office() {
        return "rental_office";
    }

    @GetMapping("/course")
    public String course(){
        return "course";
    }

    @GetMapping("/course_1")
    public String course_1(){
        return "course_1";
    }

    @GetMapping("/mycourse")
    public String mycourse(){
        return "mycourse";
    }

    @GetMapping("/profile/{username}")
    public String profile(Model model, @PathVariable("username") String username) throws Exception {
        SiteUser siteUser = this.userService.getUser(username);
        model.addAttribute("siteUser", siteUser);
        return "profile_form";
    }

}
