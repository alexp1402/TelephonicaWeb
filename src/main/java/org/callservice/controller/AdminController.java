package org.callservice.controller;

import jakarta.validation.Valid;
import org.callservice.entity.ServiceBillingType;
import org.callservice.model.TelephoneServiceForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminMenu(){
        return "Admin";
    }

    @GetMapping("/admin/addTelephoneService")
//    @ModelAttribute
    public String addNewTelephoneService(Model model){
        model.addAttribute("service",new TelephoneServiceForm());
        model.addAttribute("action", "/admin/addTelephoneService");
        model.addAttribute("readonly",false);
        model.addAttribute("billingEnum", ServiceBillingType.values());
        return "AddService";
    }

    @PostMapping("/admin/addTelephoneService")
    public String addTelephoneService(@ModelAttribute("service") @Valid TelephoneServiceForm tService, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "AddService";
        }
        //TelephoneServiceForm ts =(TelephoneServiceForm) model.getAttribute("service");

        System.out.println(tService.getName());
        //call repo to write service in DB

        return "redirect:/admin";
    }




//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/admin")
//    public String userList(Model model) {
//        model.addAttribute("allUsers", userService.allUsers());
//        return "admin";
//    }
//
//    @PostMapping("/admin")
//    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/admin/gt/{userId}")
//    //greater 24
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin";
//    }
}
