package org.callservice.controller;


import org.callservice.model.TelephoneService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminMenu(){
        return "Admin";
    }


    @GetMapping("/admin/addTelephoneService")
    public String addNewTelephoneService(@ModelAttribute("service") TelephoneService tService){
//        model.addAttribute("service",new TelephoneServiceForm());
//        model.addAttribute("action", "/admin/addTelephoneService");
//        model.addAttribute("readonly",false);
//        model.addAttribute("billingEnum", ServiceBillingType.values());
        return "AddService";
    }

    //adding new Telephone Service controller
    @PostMapping("/admin/addTelephoneService")
    public String addTelephoneService(@ModelAttribute("service") @Valid TelephoneService tService, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "AddService";
        }


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
