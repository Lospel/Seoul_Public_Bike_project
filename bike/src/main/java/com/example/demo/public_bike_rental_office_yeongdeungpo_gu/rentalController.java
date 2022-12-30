package com.example.demo.public_bike_rental_office_yeongdeungpo_gu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rental")
public class rentalController {
    private final rentalService rentalService;
    
    @GetMapping("/rental_office")
    public String rental_office() {
        return "rental_office";
    }
}
