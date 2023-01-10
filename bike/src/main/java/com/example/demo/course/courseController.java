package com.example.demo.course;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
@RequestMapping("/course")
public class courseController {
    private final courseRepository courseRepository;

    @GetMapping(value="/savedata")
    public String savedata(@RequestParam(value = "name") String name, @RequestParam(value = "longitude") String longitude, @RequestParam(value = "latitude") String latitude) {
        course saveCourse = course.builder().name(name).longitude(longitude).latitude(latitude).createDate(LocalDateTime.now()).build();
        courseRepository.save(saveCourse);
        return "redirect:/rental/mycourse";
    }
    
}
