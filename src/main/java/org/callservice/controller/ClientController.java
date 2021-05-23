package org.callservice.controller;

import org.callservice.models.*;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private List<ClientTelephoneService> clientTS = null;

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
        Payment payment = new Payment();
        //put current client in Model
        model.addAttribute("payment", payment);
        return "Payment";
    }

    //take payment POST
    @PostMapping("/client/makePayment")
    public String takePayment(@ModelAttribute("payment") @Valid Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Payment";
        }
        //convert to BIGDECIMAL and add and then if amount > 0 active->true
        client.getAccount().setAmount(client.getAccount().getAmount() + payment.getAmount());
        System.out.println(client.getAccount().getAmount());
        return "redirect:/client";
    }

    //view service for client page
    @GetMapping("/client/viewClientService")
    public String servicePage(Model model) {
        //call method compare all service with client's service and return ClientTelephoneService
        if (clientTS == null) {
            List<TelephoneService> all = (List<TelephoneService>) telephoneServiceService.findAll();
            clientTS = new ArrayList<>();
            for (TelephoneService service : all) {
                clientTS.add(new ClientTelephoneService(service, false));
            }
        }
        //need new object servicelist
        model.addAttribute("services", clientTS);
        return "ClientService";
    }


    //buy service command
    @PatchMapping("/client/buyService/{id}")
    public String buyService(@PathVariable("id") Long id) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //make status in clientTS - buy (true)
        //add tService(id) to client serviceList in DB
        for (ClientTelephoneService ts : clientTS) {
            if (ts.gettService().getId() == id) {
                ts.setBayed(true);
            }
        }
        //System.out.println("ID->" + id);
        return "redirect:/client/viewClientService";
    }

    //disclaim service page
    @PatchMapping("/client/disclaimService/{id}")
    public String disclaimService(@PathVariable("id") Long id) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //make status in clientTS - buy (false)
        //drop tService(id) from client serviceList in DB
        for (ClientTelephoneService ts : clientTS) {
            if (ts.gettService().getId() == id) {
                ts.setBayed(false);
            }
        }
        System.out.println("ID->" + id);
        //model.addAttribute("services", clientTS);
        return "redirect:/client/viewClientService";
    }

}
