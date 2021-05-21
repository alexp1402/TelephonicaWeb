package org.callservice.controller;


import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.TelephoneServiceRepo;
import org.callservice.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AdminController {

    private TelephoneServiceRepo telephoneService;
    private EmailValidator emailValidator;

    @Autowired
    public AdminController(TelephoneServiceRepo telephoneService, EmailValidator emailValidator) {
        this.telephoneService = telephoneService;
        this.emailValidator=emailValidator;
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
        model.addAttribute("service",telephoneService.findById(id).get());
        return "EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "EditService";
        telephoneService.save(service);
        return "redirect:/admin/viewTelephoneService";
    }

    //call add Client page
    @GetMapping("/admin/addClient")
    //@ModelAttribute("client") Client client
    public String addNewClient(Model model)
    {
        Client clientn = new Client();
        model.addAttribute("client",clientn);
        return "AddClient";
    }

    //store new client
    @PostMapping("/admin/addClient")
    public String storeClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult){
        //check for unique email
        emailValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()){
            return "AddClient";
        }

        //create new AccountAbonent and bind it with new Client

        //store in db new client !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        System.out.println(client.getFirstName() + " "+client.getEmail()+" "+client.getPassword());
        System.out.println("STATUS -"+client.isActive());
        return "redirect:/admin";
    }

    //call view ClientService page
    @GetMapping("/admin/viewClients")
    public String viewClients(Model model){
        Client cl1 = new Client();
        cl1.setId(1L);
        cl1.setFirstName("first");
        cl1.setSecondName("first");
        cl1.setEmail("first");
        cl1.setActive(true);
        cl1.setAccountId(5L);
        Client cl2 = new Client();
        cl2.setId(2L);
        cl2.setFirstName("second");
        cl2.setSecondName("second");
        cl2.setEmail("second");
        cl2.setActive(false);
        cl2.setAccountId(-5L);
        List<Client> cll = new ArrayList<Client>();
        cll.add(cl1);
        cll.add(cl2);
        model.addAttribute("clients",cll);
        return "ViewClients";
    }


}
