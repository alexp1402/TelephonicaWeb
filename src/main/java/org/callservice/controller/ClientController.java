package org.callservice.controller;

import org.callservice.models.*;
import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.service.AccountService;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ClientController {

    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;
    private AccountService accountService;
    private Logger log;

//    /////////del it
//    private Account account;
//    private Client client;
//    private List<ClientTelephoneServices> clientTS = null;

    @Autowired
    public ClientController(ClientService clientService,
                            TelephoneServiceService telephoneServiceService,
                            AccountService accountService,
                            Logger log) {
        this.clientService = clientService;
        this.telephoneServiceService = telephoneServiceService;
        this.accountService = accountService;
        this.log = log;
    }

    //main client page
    @GetMapping("/client")
    public String clientMenu(Model model, Principal principal) {
        //get full client by client_name in principal (email)
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        log.debug("Call client page for client {}",principal.getName());
        return "client/Client";
    }
}
