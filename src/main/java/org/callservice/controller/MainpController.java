package org.callservice.controller;


import org.callservice.service.UserInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainpController {
    @Autowired
    private UserInitService uService;

    @GetMapping("/")
    public String mainPage(){
        return "Index";
    }

    @GetMapping("/init")
    public String initSite(){
        uService.initRolesAdmin();
        return "redirect:/";
    }
}
