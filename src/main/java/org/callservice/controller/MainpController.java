package org.callservice.controller;

import org.callservice.service.UserInitService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainpController {

    @Autowired
    private Logger log;

    @Autowired
    private UserInitService uService;

    @GetMapping("/")
    public String mainPage() {
        log.debug("Call main page (Index view)");
        return "Index";
    }

    @GetMapping("/init")
    public String initSite() {
        uService.initRolesAdmin();
        log.debug("Init parameter and redirect to main page");
        return "redirect:/";
    }

    // Login form
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        log.debug("Call login page for ip {}",request.getRemoteAddr());
        return "loginout/login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        log.error("Error during login Check password and email Redirect to login page");
        return "loginout/login";
    }

    //Logout form
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.debug("Call logout page");
        return "loginout/logout";
    }
}
