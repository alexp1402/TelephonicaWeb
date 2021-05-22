package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClientService {


    ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
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

    public Object getCheckedServiceList(Long id){
        //List<>
        return null;

    }
}
