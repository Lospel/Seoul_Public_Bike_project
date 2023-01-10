package com.example.demo.public_bike_rental_office_yeongdeungpo_gu;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rental")
public class rentalController {
    private final UserService userService;
    private final rentalService rentalService;
    private final seoulService seoulService;
    private final CourseRepository courseRepository;
    
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
    // @GetMapping("/save")
    // public String save(HttpServletRequest req){
    //     String place = req.getParameter("name1");
    //     String lng = req.getParameter("longitude1");
    //     String lat = req.getParameter("latitude1");
    //     System.out.println("places:"+place);
    //     System.out.println("lng:"+lng);
    //     System.out.println("lat:"+lat);
    //     return "redirect:/rental/mycourse";
    // }
    @GetMapping("/save")
    public String save(@RequestParam(value="result[]")List<String> result ){
        for(String place : result){
            System.out.println(place);
            // SiteUser siteUser = this.userService.getUserID(principal.getID());
            Course entity = Course.builder().place(place).build();
            courseRepository.save(entity);
        }
        return "redirect:/rental/mycourse";
    }
    
}
