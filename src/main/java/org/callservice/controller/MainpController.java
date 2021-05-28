package org.callservice.controller;


import org.callservice.service.UserInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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


    // Login form
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
