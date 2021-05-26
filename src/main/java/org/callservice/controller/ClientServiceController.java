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
        //get all services marked bayed or not by client
        if (clientTelephoneServices.isEmpty()) {
            clientTelephoneServices = clientService.getClientMarkedServices(clientService.findByEmail(principal.getName()));
        }
        //add to Model to show on page
        model.addAttribute("services", clientTelephoneServices);

        return "ClientService";
    }


    //buy service command
    @PatchMapping("/client/buyService/{id}")
    public String buyService(@PathVariable("id") Long id) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //make status in clientTS - buy (true)
        //add tService(id) to client serviceList in DB
//        for (ClientTelephoneServices ts : clientTS) {
//            if (ts.getService().getId() == id) {
//                ts.setBayed(true);
//            }
//        }
        //System.out.println("ID->" + id);
        return "redirect:/client/viewClientService";
    }

    //disclaim service page
    @PatchMapping("/client/disclaimService/{id}")
    public String disclaimService(@PathVariable("id") Long id) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //make status in clientTS - buy (false)
        //drop tService(id) from client serviceList in DB
//        for (ClientTelephoneServices ts : clientTS) {
//            if (ts.getService().getId() == id) {
//                ts.setBayed(false);
//            }
//        }
//        System.out.println("ID->" + id);
        //model.addAttribute("services", clientTS);
        return "redirect:/client/viewClientService";
    }
}
