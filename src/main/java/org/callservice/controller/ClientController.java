package org.callservice.controller;

import org.callservice.models.*;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    private ClientService clientService;
    private TelephoneServiceService telephoneServiceService;

    /////////del it
    private Account account;
    private Client client;

    @Autowired
    public ClientController(ClientService clientService, TelephoneServiceService telephoneServiceService) {
        this.clientService = clientService;
        this.telephoneServiceService = telephoneServiceService;
    }

    //main client page
    @GetMapping("/client")
    public String clientMenu(Model model) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //put current client in model
        if (client == null) {
            client = new Client();
            client.setId(21L);
            client.setFirstName("Alexp");
            client.setSecondName("Plesk");
            account = new Account(2.25);
            client.setAccount(account);
            client.setActive(true);
        }
        model.addAttribute("client", client);
        return "Client";
    }

    //view payload page
    @GetMapping("client/payment")
    public String payloadPage(Model model) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //payment amount to model
        Payment payment = new Payment(1L);
        //put current client in Model
        model.addAttribute("payment", payment);
//        model.addAttribute("client", client);
        return "Payment";
    }

    //view service for client page
    @GetMapping("client/viewClientService")
    public String servicePage(Model model) {
        //call method compare all service with client's service and return ClientTelephoneService
        List<TelephoneService> all = (List<TelephoneService>) telephoneServiceService.findAll();
        List<ClientTelephoneService> clserv = new ArrayList<>();
        for(TelephoneService service : all){
            clserv.add(new ClientTelephoneService(service,false));
        }
        //need new object servicelist
        model.addAttribute("services", clserv);
        return "ClientService";
    }

    //take payment POST
    @PostMapping("/client/makePayment")
    public String takePayment(@ModelAttribute("payment") @Valid Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Payment";
        }
        //convert to BIGDECIMAL and add and then if amount > 0 active->true
        client.getAccount().setAmount(client.getAccount().getAmount()+payment.getAmount());
        System.out.println(client.getAccount().getAmount());
        return "redirect:/client";
    }


}
