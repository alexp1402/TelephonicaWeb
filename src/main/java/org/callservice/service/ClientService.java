package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.callservice.repositories.ClientRepo;
import org.callservice.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ClientService{

    private PasswordEncoder passwordEncoder;
    private ClientRepo clientRepo;
    private TelephoneServiceService telephoneService;
    private RoleRepo roleRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo,TelephoneServiceService telephoneService, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.clientRepo = clientRepo;
        this.telephoneService=telephoneService;
        this.passwordEncoder=passwordEncoder;
        this.roleRepo = roleRepo;
    }

    //save new client. For exist client use update
    public void save(Client client) {
        client.setAccount(new Account(new BigDecimal(0.0)));
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        //by default all new Client will be USER
        if (client.getRole()==null){
            Set<Role> role = new HashSet<>();
            role.add(roleRepo.getByName("ROLE_USER"));
            client.setRole(role);
        }
        clientRepo.save(client);
    }

    //save existing client
    @Transactional
    public void update(Long id, Client client) {
        Client existClient = clientRepo.getById(id);
        if (existClient == null)
            throw new IllegalArgumentException("No such client in db clientId to update" + id);
        client.setAccount(existClient.getAccount());
        client.setRole(existClient.getRole());
        //? what with Count??????????????
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

    public Client findByEmail(String email) {
        return clientRepo.findByEmail(email);
    }

    //return all client with role User
    @Transactional
    public Object findAllClientWithUserRole() {
        Role role = roleRepo.getByName("ROLE_USER");
        List<Client> clients = clientRepo.findWithRole(role);
        return clients;
    }
}
