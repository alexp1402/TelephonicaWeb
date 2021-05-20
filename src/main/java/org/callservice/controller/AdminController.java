package org.callservice.controller;


import org.callservice.model.TelephoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

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

        //call repo to write service in DB ????????????????????????????????????????

        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model){
        //take all services from db ??????????????????????????????????????????
        List<TelephoneService> services = new ArrayList<>();
        TelephoneService ts= new TelephoneService();
        ts.setName("First");
        ts.setDescription("Description");
        ts.setCost(2.5);
        ts.setId(1L);
        TelephoneService ts2= new TelephoneService();
        services.add(ts);
        ts2.setName("Second");
        ts2.setDescription("Description");
        ts2.setCost(2.5);
        ts2.setId(2L);
        services.add(ts2);

        model.addAttribute("services",services);
        return "ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id")Long id, Model model){
        //take service by id ????????????????????????????????????????????????????
        TelephoneService ts = new TelephoneService();
        ts.setName("Test name");
        ts.setDescription("Description");
        ts.setCost(2.5);
        ts.setId(1L);
        model.addAttribute("service",ts);
        return "EditService";
    }

    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "EditService";

        System.out.println("NEW NAME - "+service.getName());
        //store service information by id ????????????????????????????????????
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
