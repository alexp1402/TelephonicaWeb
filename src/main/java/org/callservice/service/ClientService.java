package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.repositories.ClientRepo;
import org.callservice.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ClientService {

    private PasswordEncoder passwordEncoder;
    private ClientRepo clientRepo;
    private TelephoneServiceService telephoneService;
    private RoleRepo roleRepo;
    private boolean sortCount=false;

    @Autowired
    public ClientService(ClientRepo clientRepo, TelephoneServiceService telephoneService, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.clientRepo = clientRepo;
        this.telephoneService = telephoneService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    //save new client. For exist client use update
    public void save(Client client) {
        //default 0.0 money
        if (client.getAccount() == null) {
            client.setAccount(new Account(new BigDecimal(0.0)));
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        //by default all new Client will be USER
        if (client.getRole() == null) {
            Set<Role> role = new HashSet<>();
            role.add(roleRepo.getByName("ROLE_USER"));
            client.setRole(role);
        }
        if (client.getServices() == null) {
            client.setServices(new HashSet<>());
        }
        clientRepo.save(client);
    }

    //save existing client
    //@Transactional
    //Long id,
    public void update(Client client) {
        clientRepo.save(client);
    }

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> client = clientRepo.findById(id);
        if (!client.isPresent())
            throw new IllegalArgumentException("No such client in DB id=" + id);
        return client.get();
    }

    public Boolean emailInUse(Long client_id, String email) {
        Client find = clientRepo.findByEmail(email);
        //if no such email or found Client.id equals client_id (patch client data)
        if (find == null || find.getId().equals(client_id)) {
            return false;
        }
        return true;
    }

    //return List of all services marked which are bayed by client and which not
    @Transactional
    public List<ClientTelephoneServices> getClientMarkedServices(String clientEmail) {
        Set<TelephoneService> clientService = findByEmail(clientEmail).getServices();
        List<ClientTelephoneServices> markedServices = new ArrayList<>();
        ((List<TelephoneService>) telephoneService.findAll()).stream()
                .forEach(ts -> markedServices.add(
                        new ClientTelephoneServices(ts, clientService.contains(ts))));
        return markedServices;
    }

    public Client findByEmail(String email) {
        return clientRepo.findByEmail(email);
    }

    //return all client with role User
    @Transactional
    public List<Client> findAllClientWithUserRole() {
        Role role = roleRepo.getByName("ROLE_USER");
        List<Client> clients = clientRepo.findWithRole(role);
        return clients;
    }

    //add new service
    @Transactional
    public void addService(Long serviceId, String email) {
        //find client by email
        Client client = findByEmail(email);
        //add service(by id) in clients services set
        client.getServices().add(telephoneService.findById(serviceId));
        //store
        update(client);
    }

    //delete service
    @Transactional
    public void deleteService(Long serviceId, String email) {
        //find client by email
        Client client = findByEmail(email);
        //delete service(by id) from clients services set
        client.getServices().remove(telephoneService.findById(serviceId));
        //store
        update(client);
    }


    public boolean getActive(String email) {
        return findByEmail(email).isActive();
    }

//    @Transactional
    public void adminUpdate(Long clientId, Client clientData) {
        Client currentClient = findById(clientId);
        currentClient.setFirstName(clientData.getFirstName());
        currentClient.setSecondName(clientData.getSecondName());
        currentClient.setEmail(clientData.getEmail());
        currentClient.setActive(clientData.isActive());
        update(currentClient);
    }

//    @Transactional
    public void updatePassword(Long clientId, String password) {
        Client client = findById(clientId);
        client.setPassword(passwordEncoder.encode(password));
        update(client);
    }

    public List<Client> findAllClientsOrderedByCount(){
        sortCount=sortCount?false:true;
        Sort.Direction direction = sortCount?Sort.Direction.ASC:Sort.Direction.DESC;
        return clientRepo.findAll(Sort.by(direction, "account.amount"));
    }
}
