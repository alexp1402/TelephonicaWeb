package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.AccountRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SimpleBillService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Logger log;

    @Transactional
    public void makeBill(){
        List<Client> clients = (ArrayList<Client>) clientService.findAll();
        log.debug("START bill for clients:");
        for(Client client:clients){
            //if client is blocked, no bill work for him
            if (client.isActive()) {
                Set<TelephoneService> services = client.getServices();
                //if client has no one service - nothing to bill
                if (!services.isEmpty()) {
                    Account account = client.getAccount();
                    for (TelephoneService service : services) {
                        account.setAmount(account.getAmount().subtract(service.getCost()));
                    }
                    accountService.update(account);
                    log.debug("Bill for client ({}) for services ({}) Store new data in account={}",client.getEmail(), services.toArray(), account.getAmount());
                }
            }
        }
        log.debug("bill END");
    }
}
