package org.callservice.controller;

import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.models.TelephoneService;
import org.callservice.service.ClientService;
import org.callservice.service.TelephoneServiceService;
import org.slf4j.Logger;
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
    //list for services Checked are bayed by client or not
    private List<ClientTelephoneServices> clientTelephoneServices;
    private ClientService clientService;
    private Logger log;

    @Autowired
    public ClientServiceController(TelephoneServiceService telephoneServiceService,
                                   ClientService clientService,
                                   Logger log) {
        this.telephoneServiceService = telephoneServiceService;
        this.clientService = clientService;
        this.log = log;
        clientTelephoneServices = new ArrayList<>();
    }

    //view service for client page
    @GetMapping("/client/viewClientService")
    public String servicePage(Model model, Principal principal) {
        //get all services marked bayed or not by client and add to Model to show on page
        model.addAttribute("services", clientService.getClientMarkedServices(principal.getName()));
        //if blocked can't bay service only disclaim
        model.addAttribute("active",clientService.getActive(principal.getName()));
        log.debug("Call for client ({}) services page",principal.getName());
        return "client/ClientService";
    }

    //buy service command
    @PatchMapping("/client/buyService/{id}")
    public String buyService(@PathVariable("id") Long id, Principal principal) {
        clientService.addService(id,principal.getName());
        log.debug("Client ({}) bayed service (id={}) successfully Redirect to view client's services", principal.getName(), id);
        return "redirect:/client/viewClientService";
    }

    //disclaim service page
    @PatchMapping("/client/disclaimService/{id}")
    public String disclaimService(@PathVariable("id") Long id, Principal principal) {
        clientService.deleteService(id, principal.getName());
        log.debug("Client ({}) disclaimed service id={}",principal.getName(),id);
        return "redirect:/client/viewClientService";
    }
}
