package org.callservice.controller;


import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.AccountRepo;
import org.callservice.repositories.ClientRepo;
import org.callservice.repositories.TelephoneServiceRepo;
import org.callservice.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AdminController {

    private TelephoneServiceRepo telephoneServiceRepo;
    private ClientRepo clientRepo;
    private EmailValidator emailValidator;

    @Autowired
    public AdminController(TelephoneServiceRepo telephoneService,
                           EmailValidator emailValidator,
                           ClientRepo clientRepo) {
        this.telephoneServiceRepo = telephoneService;
        this.emailValidator=emailValidator;
        this.clientRepo = clientRepo;
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
        telephoneServiceRepo.save(tService);
        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model){
        model.addAttribute("services", telephoneServiceRepo.findAll());
        return "ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id")Long id, Model model){
        model.addAttribute("service", telephoneServiceRepo.findById(id).get());
        return "EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id")Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "EditService";
        telephoneServiceRepo.save(service);
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

        //create new Account for Abonent and bind it with new Client
        Account account = new Account(0.0);
        client.setAccount(account);
        //store Client in db
        clientRepo.save(client);
        return "redirect:/admin";
    }

    //call view ClientService page
    @GetMapping("/admin/viewClients")
    public String viewClients(Model model){
         model.addAttribute("clients",clientRepo.findAll());
        return "ViewClients";
    }

    //call edit client page with client object
    @GetMapping("admin/editClient/{id}")
    public String editClient(@PathVariable("id")Long id, Model model){
//        Client cl1 = new Client();
//        cl1.setId(1L);
//        cl1.setFirstName("first");
//        cl1.setSecondName("second");
//        cl1.setEmail("some@some.by");
//        cl1.setActive(true);
//        cl1.setPassword("123456");
//      // cl1.setAccountId(5L);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //model.addAttribute("service",telephoneService.findById(id).get());
        model.addAttribute("client",clientRepo.findById(id).get());
        return "EditClient";
    }


    //update existing service in db
    @PatchMapping("/admin/editClient/{id}")
    public String patchSClient(@PathVariable("id")Long id, @ModelAttribute("client") Client client, BindingResult bindingResult){
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if(bindingResult.hasErrors())
            return "EditClient";

        Client existClient = clientRepo.getById(id);
        existClient.setFirstName(client.getFirstName());
        existClient.setSecondName(client.getSecondName());
        existClient.setPassword(client.getPassword());
        existClient.setEmail(client.getEmail());
        clientRepo.save(existClient);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        System.out.println(client.getFirstName()+" status-"+client.isActive()+" amount->"+client.getAccount().getId()+" amount="+client.getAccount().getAmount());
        return "redirect:/admin/viewClients";
    }


}
