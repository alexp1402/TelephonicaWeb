package org.callservice.controller;

import org.callservice.entity.TestEntity;
import org.callservice.repository.TestEntityRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import java.text.SimpleDateFormat;

@Controller
public class TestController {

    @GetMapping("/")
    public String printWelcome(Model model, TestEntityRepo ter){
        model.addAttribute("message", "Spring MVC Hello");
        LocalDate date = LocalDate.now();
        model.addAttribute("date",date);

        TestEntity te = new TestEntity();
        te.setTest("hello");
        ter.save(te);

        //model.addAttribute("active","true");
        return "index";
    }

}
