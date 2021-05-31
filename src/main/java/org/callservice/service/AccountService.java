package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Payment;
import org.callservice.repositories.AccountRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {

    @Autowired
    Logger log;

    @Autowired
    private AccountRepo accountRepo;

    public void addPayment(Payment payment, Client client){
        //set payment date
        payment.setLocalDateTime(LocalDateTime.now());
        //increase Account.amount
        client.getAccount().setAmount(client.getAccount().getAmount().add(payment.getAmount()));
        //store payment in db
        client.getAccount().getPayments().add(payment);
        //if amount>0 then client.status = true
        if (client.getAccount().getAmount().doubleValue()>0){
            client.setActive(true);
        }
        //update client's account
        accountRepo.save(client.getAccount());
        log.debug("Add and store payment ({}) for client ({})", payment, client);
    }

    public void update(Account account){
        accountRepo.save(account);
    }
}
