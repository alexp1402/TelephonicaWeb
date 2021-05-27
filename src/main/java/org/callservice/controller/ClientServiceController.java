package org.callservice.controller;

import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.models.TelephoneService;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientServiceController {

    private TelephoneServiceService telephoneServiceService;
    private List<ClientTelephoneServices> clientTelephoneServices;
    private ClientService clientService;

    @Autowired
    public ClientServiceController(TelephoneServiceService telephoneServiceService, ClientService clientService) {
        this.telephoneServiceService = telephoneServiceService;
        this.clientService = clientService;
        clientTelephoneServices = new ArrayList<>();
    }

    //view service for client page
    @GetMapping("/client/viewClientService")
    public String servicePage(Model model, Principal principal) {
        //get all services marked bayed or not by client and add to Model to show on page
        model.addAttribute("services", clientService.getClientMarkedServices(principal.getName()));
        return "ClientService";
    }


    //buy service command
    @PatchMapping("/client/buyService/{id}")
    public String buyService(@PathVariable("id") Long id, Principal principal) {
        clientService.addService(id,principal.getName());
        return "redirect:/client/viewClientService";
    }

    //disclaim service page
    @PatchMapping("/client/disclaimService/{id}")
    public String disclaimService(@PathVariable("id") Long id, Principal principal) {
        clientService.deleteService(id, principal.getName());
        return "redirect:/client/viewClientService";
    }
}
