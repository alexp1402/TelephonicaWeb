package org.callservice.controller;

import org.callservice.models.Payment;
import org.callservice.service.AccountService;
import org.callservice.service.ClientService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class PaymentController {

    private ClientService clientService;
    private AccountService accountService;
    private Logger log;

    @Autowired
    public PaymentController(ClientService clientService,
                             AccountService accountService,
                             Logger log) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.log = log;
    }

    //view payload page
    @GetMapping("client/payment")
    public String payloadPage(Model model) {
        //put in model new Payment object to fill it through client's data
        model.addAttribute("payment", new Payment());
        log.debug("Call payment page");
        return "payment/Payment";
    }

    //take payment POST
    @Transactional
    @PostMapping("/client/makePayment")
    public String takePayment(@ModelAttribute("payment") @Valid Payment payment, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "payment/Payment";
        }
        //find client by email in Principal and call AccountService to make change and store in db
        accountService.addPayment(payment, clientService.findByEmail(principal.getName()));
        log.debug("Payment {} done. Redirect to client page", payment);
        return "redirect:/client";
    }

}
