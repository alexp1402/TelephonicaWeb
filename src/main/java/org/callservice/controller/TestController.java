package org.callservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDate;


@Controller
public class TestController {

    @GetMapping("/")
    public String printWelcome(Model model){
        model.addAttribute("message", "Spring MVC Hello");
        LocalDate date = LocalDate.now();
        model.addAttribute("date",date);

        //model.addAttribute("active","true");
        return "index";
    }

}
