package org.callservice.controller;


import org.callservice.models.TelephoneService;
import org.callservice.repositories.TelephoneServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {

    private TelephoneServiceRepo telephoneService;

    @Autowired
    public AdminController(TelephoneServiceRepo telephoneService) {
        this.telephoneService = telephoneService;
    }

    //main admin page
    @GetMapping("/admin")
    public String adminMenu(){
        return "Admin";
    }

    //call add TelephoneService page
    @GetMapping("/admin/addTelephoneService")
    public String addNewTelephoneService(@ModelAttribute("service") TelephoneService tService){
        return "AddService";
    }

    //adding (storing) new Telephone Service controller
    @PostMapping("/admin/addTelephoneService")
    public String saveTelephoneService(@ModelAttribute("service") @Valid TelephoneService tService, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "AddService";
        }
        telephoneService.save(tService);
        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model){
        model.addAttribute("services",telephoneService.findAll());
        return "ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id")Long id, Model model){
//        Optional<TelephoneService> tService = telephoneService.findById(id);
//        tService.
        model.addAttribute("service",telephoneService.findById(id).get());
        return "EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "EditService";
        //if we call save with existing id it's like update method
        telephoneService.save(service);
        return "redirect:/admin/viewTelephoneService";
    }

//    @PutMapping("admin/patchTelephoneService/{id}")
//    public String putService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
//        if(bindingResult.hasErrors())
//            return "EditService";
//
//        System.out.println("NEW NAME - "+service.getName());
//        //store service information by id
//        return "redirect:/admin/viewTelephoneService";
//    }


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
