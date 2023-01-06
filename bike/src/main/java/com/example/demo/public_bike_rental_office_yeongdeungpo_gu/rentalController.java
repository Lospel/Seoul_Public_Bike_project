package com.example.demo.public_bike_rental_office_yeongdeungpo_gu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rental")
public class rentalController {
    private final rentalService rentalService;
    private final seoulService seoulService;
    
    @GetMapping("/rental_office")
    public String rental_office(Model model, @RequestParam(value = "keyword", defaultValue = "국회의원회관")String keyword) {
        model.addAttribute("offices", rentalService.getAllrental());
        model.addAttribute("place", rentalService.getplace(keyword));
        return "rental_office";
    }
    @GetMapping("/mycourse")
    public String mycourse(Model model) {
        model.addAttribute("offices", rentalService.getAllrental());
        model.addAttribute("places", seoulService.getAllplaces());
        return "mycourse";
    }
}
