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
    @RequestMapping("/login")
    public String login() {
<<<<<<< HEAD
        return "login.html";
=======
        return "loginout/login";
>>>>>>> 385a5cdca500bb53608f60dd30e3d71907648232
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
<<<<<<< HEAD
        return "login.html";
=======
        return "loginout/login";
    }

    //Logout form
    @RequestMapping("/logout")
    public String logout(){
        return "loginout/logout";
>>>>>>> 385a5cdca500bb53608f60dd30e3d71907648232
    }
}
