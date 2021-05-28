package org.callservice.controller;

import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.service.ClientService;
import org.callservice.service.SimpleBillService;
import org.callservice.service.TelephoneServiceService;
import org.callservice.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
public class AdminController {


    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;
    private EmailValidator emailValidator;
    private SimpleBillService simpleBillService;

    @Autowired
    public AdminController(ClientService clientService, TelephoneServiceService telephoneServiceService, EmailValidator emailValidator, SimpleBillService simpleBillService) {
        this.telephoneServiceService = telephoneServiceService;
        this.emailValidator = emailValidator;
        this.clientService = clientService;
        this.simpleBillService = simpleBillService;
    }

    //main admin page
    @GetMapping("/admin")
    public String adminMenu() {
        return "admin/Admin";
    }

    //call add TelephoneService page
    @GetMapping("/admin/addTelephoneService")
    public String addNewTelephoneService(@ModelAttribute("service") TelephoneService tService) {
        return "admin/AddService";
    }

    //adding (storing) new Telephone Service controller
    @PostMapping("/admin/addTelephoneService")
    public String saveTelephoneService(@ModelAttribute("service") @Valid TelephoneService tService, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/AddService";
        }
        telephoneServiceService.save(tService);
        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model) {
        model.addAttribute("services", telephoneServiceService.findAll());
        return "admin/ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id") Long id, Model model) {
        model.addAttribute("service", telephoneServiceService.findById(id));
        return "admin/EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id") Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "EditService";
        telephoneServiceService.update(service);
        return "redirect:/admin/viewTelephoneService";
    }

    //call add Client page
    @GetMapping("/admin/addClient")
    public String addNewClient(Model model) {
        Client clientn = new Client();
        model.addAttribute("client", clientn);
        return "admin/AddClient";
    }

    //store new client
    @PostMapping("/admin/addClient")
    public String storeClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/AddClient";
        }
        //store Client in db
        clientService.save(client);
        return "redirect:/admin";
    }

    //call view ClientService page
    @GetMapping("/admin/viewClients")
    public String viewClients(Model model) {
        model.addAttribute("clients", clientService.findAllClientWithUserRole());
        return "admin/ViewClients";
    }

    //call edit client page with client object
    @GetMapping("admin/editClient/{id}")
    public String editClient(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        return "admin/EditClient";
    }


    //update existing service in db
    @PatchMapping("/admin/editClient/{id}")
    public String patchClient(@PathVariable("id") Long id, @ModelAttribute("client") Client client, BindingResult bindingResult) {
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/EditClient";
        clientService.adminUpdate(id, client);
        return "redirect:/admin/viewClients";
    }

    //view change password Page
    @GetMapping("admin/editClient/changePassword/{id}")
    public String changePassword(@PathVariable("id") Long id, Model model) {
        model.addAttribute("clientId", id);
        model.addAttribute("pass", "");
        return "admin/EditPassword";
    }

    //update password
    @PatchMapping("/admin/editClient/changePassword/{id}")
    public String patchClient(@PathVariable("id") Long id, @ModelAttribute("pass") String password, BindingResult bindingResult) {
        //validate password forgot
        clientService.updatePassword(id, password);
        return "redirect:/admin/viewClients";
    }

    //make bill
    @GetMapping("/admin/viewClients/bill")
    public String makeBill() {
        simpleBillService.makeBill();
        return "redirect:/admin/viewClients";
    }

    //sort clients page by client.account.amount
    @GetMapping("/admin/viewClients/sortCount")
    public String orderedClientsByAccount(Model model){
        model.addAttribute("clients", clientService.findAllClientsOrderedByCount());
        return "forward:admin/ViewClients";
    }
}
