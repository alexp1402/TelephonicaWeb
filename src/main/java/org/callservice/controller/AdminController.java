package org.callservice.controller;

import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.callservice.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
public class AdminController {


    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;
    private EmailValidator emailValidator;

    @Autowired
    public AdminController(ClientService clientService,TelephoneServiceService telephoneServiceService, EmailValidator emailValidator) {
        this.telephoneServiceService = telephoneServiceService;
        this.emailValidator = emailValidator;
        this.clientService=clientService;
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
        telephoneServiceService.save(tService);
        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model){
        model.addAttribute("services", telephoneServiceService.findAll());
        return "ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id")Long id, Model model){
        model.addAttribute("service", telephoneServiceService.findById(id));
        return "EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "EditService";
        telephoneServiceService.update(service);
        return "redirect:/admin/viewTelephoneService";
    }

    //call add Client page
    @GetMapping("/admin/addClient")
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
        //store Client in db
        clientService.save(client);
        return "redirect:/admin";
    }

    //call view ClientService page
    @GetMapping("/admin/viewClients")
    public String viewClients(Model model){
         model.addAttribute("clients",clientService.findAllClientWithUserRole());
        return "ViewClients";
    }

    //call edit client page with client object
    @GetMapping("admin/editClient/{id}")
    public String editClient(@PathVariable("id")Long id, Model model){
        model.addAttribute("client",clientService.findById(id));
        return "EditClient";
    }


    //update existing service in db
    @PatchMapping("/admin/editClient/{id}")
    public String patchSClient(@PathVariable("id")Long id, @ModelAttribute("client") Client client, BindingResult bindingResult){
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if(bindingResult.hasErrors())
            return "EditClient";

        clientService.update(id, client);

        return "redirect:/admin/viewClients";
    }


}
