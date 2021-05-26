package org.callservice.controller;

import org.callservice.models.*;
import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.service.AccountService;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
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

    /////////del it
    private Account account;
    private Client client;
    private List<ClientTelephoneServices> clientTS = null;

    @Autowired
    public ClientController(ClientService clientService,
                            TelephoneServiceService telephoneServiceService,
                            AccountService accountService) {
        this.clientService = clientService;
        this.telephoneServiceService = telephoneServiceService;
        this.accountService = accountService;
    }

    //main client page
    @GetMapping("/client")
    public String clientMenu(Model model, Principal principal) {
        if (principal==null){
            throw new SecurityException("DANGER Security breach No authentication incoming for client in /client/");
        }
        //get full client by client_name in principal (email)
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        return "Client";
    }
}
