package org.callservice.controller;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;

    @Autowired
    public ClientController(ClientService clientService, TelephoneServiceService telephoneServiceService) {
        this.clientService = clientService;
        this.telephoneServiceService = telephoneServiceService;
    }

    //main client page
    @GetMapping("/client")
    public String clientMenu(Model model) {
        Client cl = new Client();
        cl.setFirstName("Alexp");
        cl.setSecondName("Plesk");
        Account ac = new Account(2.25);
        cl.setAccount(ac);
        cl.setActive(false);

        model.addAttribute("client",cl);
        return "Client";
    }

    //view payload page
    @GetMapping("client/payload")
    public String payloadPage(){
        return "Payload";
    }

    //view service for client page
    @GetMapping("client/viewClientService")
    public String servicePage(){
        return "ClientService";
    }

}
