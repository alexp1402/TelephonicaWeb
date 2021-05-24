package org.callservice.controller;


import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.RoleRepo;
import org.callservice.service.ClientService;
import org.callservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Controller
public class MainpController {
    @Autowired
    UserService uService;

    @GetMapping("/")
    public String mainPage(){
        return "Index";
    }

    @GetMapping("/init")
    public String initSite(){
        uService.initRolesAdmin();
        return "redirect:/";
    }
//    @Autowired
//    private RoleRepo roleRepo;
//
//    @Autowired
//    private ClientService clientService;
//


}
