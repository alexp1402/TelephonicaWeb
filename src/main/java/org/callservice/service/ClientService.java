package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {


    ClientRepo clientRepo;
    TelephoneServiceService telephoneService;

    @Autowired
    public ClientService(ClientRepo clientRepo,TelephoneServiceService telephoneService) {
        this.clientRepo = clientRepo;
        this.telephoneService=telephoneService;
    }

    public void save(Client client) {
        client.setAccount(new Account(0.0));
        client.setRole(new Role("ROLE_USER"));
        clientRepo.save(client);
    }

    @Transactional
    public void update(Long id, Client client) {
        Client existClient = clientRepo.getById(id);
        if (existClient == null)
            throw new IllegalArgumentException("No such client in db clientId" + id);

        client.setAccount(existClient.getAccount());
        client.setRole(existClient.getRole());
        clientRepo.save(client);
    }


    public Object findAll() {
        return clientRepo.findAll();
    }

    public Object findById(Long id) {
        Optional<Client> client = clientRepo.findById(id);
        if (!client.isPresent())
            throw new IllegalArgumentException("No such client in DB id="+id);

        return client.get();
    }

    public Boolean emailInUse(String email){
        if(clientRepo.findByEmail(email)==null){
           return false;
        }
        return true;
    }

    public void addService(Long serviceId, Long clientId){
        //find service by id
        TelephoneService tService = (TelephoneService) telephoneService.findById(serviceId);
        //find client by id
        Client client = (Client) findById(clientId);

        Set<TelephoneService> services = client.getServices();
        if(services==null){
            //create new service
            services = new HashSet<>();
            //set it to client
            client.setServices(services);
        }
        //add service to set
        client.getServices().add(tService);
    }
}
