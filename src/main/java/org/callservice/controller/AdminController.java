package org.callservice.controller;

import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.service.ClientService;
import org.callservice.service.SimpleBillService;
import org.callservice.service.TelephoneServiceService;
import org.callservice.utils.EmailValidator;
import org.slf4j.Logger;
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
import java.security.Principal;


@Controller
public class AdminController {


    private Logger log;
    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;
    private EmailValidator emailValidator;
    private SimpleBillService simpleBillService;

    private boolean sortAmount = false;

    @Autowired
    public AdminController(ClientService clientService,
                           TelephoneServiceService telephoneServiceService,
                           EmailValidator emailValidator,
                           SimpleBillService simpleBillService,
                           Logger log) {
        this.telephoneServiceService = telephoneServiceService;
        this.emailValidator = emailValidator;
        this.clientService = clientService;
        this.simpleBillService = simpleBillService;
        this.log = log;
    }

    //main admin page
    @GetMapping("/admin")
    public String adminMenu(Principal principal) {
        log.debug("Call Admin view by {}", principal.getName());
        return "admin/Admin";
    }

    //call add TelephoneService page
    @GetMapping("/admin/addTelephoneService")
    public String addNewTelephoneService(@ModelAttribute("service") TelephoneService tService) {
        log.debug("Call AddService view");
        return "admin/AddService";
    }

    //adding (storing) new Telephone Service controller
    @PostMapping("/admin/addTelephoneService")
    public String saveTelephoneService(@ModelAttribute("service") @Valid TelephoneService tService, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Error during add new TelephoneService bindingResult={}", bindingResult.getAllErrors().toString());
            return "admin/AddService";
        }

        telephoneServiceService.save(tService);
        log.debug("New telephone service added {}", tService.toString());
        return "redirect:/admin";
    }

    //call view TelephoneService page
    @GetMapping("/admin/viewTelephoneService")
    public String viewTelephoneService(Model model) {
        model.addAttribute("services", telephoneServiceService.findAll());
        log.debug("Call view service view");
        return "admin/ViewService";
    }

    //call edit service page with service object
    @GetMapping("admin/editService/{id}")
    public String editService(@PathVariable("id") Long id, Model model) {
        model.addAttribute("service", telephoneServiceService.findById(id));
        log.debug("Call edit service view with service_id={}", id);
        return "admin/EditService";
    }

    //update existing service in db
    @PatchMapping("admin/patchTelephoneService/{id}")
    public String patchService(@PathVariable("id") Long id, @ModelAttribute("service") TelephoneService service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Error during update exist telephone service_id={} bindingResult={}", id, bindingResult);
            return "EditService";
        }
        telephoneServiceService.update(service);
        log.debug("Exist telephone service_id={} updated successfully Redirect to viewTelephoneService view", id);
        return "redirect:/admin/viewTelephoneService";
    }

    //call add Client page
    @GetMapping("/admin/addClient")
    public String addNewClient(Model model) {
        Client clientn = new Client();
        model.addAttribute("client", clientn);
        log.debug("Call addClient view");
        return "admin/AddClient";
    }

    //store new client
    @PostMapping("/admin/addClient")
    public String storeClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Error during add new client BindingResult={}", bindingResult);
            return "admin/AddClient";
        }
        //store Client in db
        clientService.save(client);
        log.debug("New client ({}) added successfully Redirected to admin page", client);
        return "redirect:/admin";
    }

    //call view ClientService page
    @GetMapping("/admin/viewClients")
    public String viewClients(Model model) {
        //sorting by count
        if (!sortAmount) {
            model.addAttribute("clients", clientService.findAllClientWithUserRole());
            log.debug("Call view clients page Sorting by amount={}", sortAmount);
        } else {
            model.addAttribute("clients", clientService.findAllClientsOrderedByCount());
            log.debug("Call view clients page Sorting by amount={}", sortAmount);
            sortAmount = false;
        }
        return "admin/ViewClients";
    }

    //call edit client page with client object
    @GetMapping("admin/editClient/{id}")
    public String editClient(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        log.debug("Call edit existing client (id={}) page", id);
        return "admin/EditClient";
    }


    //update existing service in db
    @PatchMapping("/admin/editClient/{id}")
    public String patchClient(@PathVariable("id") Long id, @ModelAttribute("client") Client client, BindingResult bindingResult) {
        //check for unique email
        emailValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Error during edit existing client_id={} Binding result=",id,bindingResult);
            return "admin/EditClient";
        }
        clientService.adminUpdate(id, client);
        log.debug("Existing client (id={}) updated successfully Redirect to viewClients page");
        return "redirect:/admin/viewClients";
    }

    //view change password Page
    @GetMapping("admin/editClient/changePassword/{id}")
    public String changePassword(@PathVariable("id") Long id, Model model) {
        model.addAttribute("clientId", id);
        model.addAttribute("pass", "");
        log.debug("Call edit password page");
        return "admin/EditPassword";
    }

    //update password
    @PatchMapping("/admin/editClient/changePassword/{id}")
    public String patchClient(@PathVariable("id") Long id, @ModelAttribute("pass") String password, BindingResult bindingResult) {
        //validate password forgot
        clientService.updatePassword(id, password);
        log.debug("Password for client_id={} updated successfully Redirect to view clients page",id);
        return "redirect:/admin/viewClients";
    }

    //make bill
    @GetMapping("/admin/viewClients/bill")
    public String makeBill() {
        simpleBillService.makeBill();
        log.debug("Call make bill ref and Redirect to view clients page");
        return "redirect:/admin/viewClients";
    }

    //sort clients page by client.account.amount
    @GetMapping("/admin/viewClients/sortAmount")
    public String orderedClientsByAccount() {
        sortAmount = true;
        log.debug("Set ordered by account-amount flag and redirect to view clients page");
        return "redirect:/admin/viewClients";
    }
}
