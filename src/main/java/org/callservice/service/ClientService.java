package org.callservice.service;

import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.callservice.models.form.ClientTelephoneServices;
import org.callservice.repositories.ClientRepo;
import org.callservice.repositories.RoleRepo;
import org.callservice.utils.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log;
    private boolean sortCount = false;

    @Autowired
    public ClientService(ClientRepo clientRepo,
                         TelephoneServiceService telephoneService,
                         PasswordEncoder passwordEncoder,
                         RoleRepo roleRepo,
                         Logger log) {
        this.clientRepo = clientRepo;
        this.telephoneService = telephoneService;
        this.passwordEncoder = passwordEncoder;
        this.log = log;
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
        log.debug("Save new client in db client={}", client);
    }

    //save existing client
    public void update(Client client) {
        clientRepo.save(client);
        log.debug("Update existed client in db with new data={}", client);
    }

    public List<Client> findAll() {
        log.debug("Select all client from db called");
        return clientRepo.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> client = clientRepo.findById(id);
        if (!client.isPresent()){
            log.error("No client in db with id={}",id);
            throw new IllegalArgumentException("No such client in DB id=" + id);
        }
        log.debug("Find by id client called and return client={}",client.get());
        return client.get();
    }

    public Boolean emailInUse(Long client_id, String email) {
        Client find = clientRepo.findByEmail(email);
        //if no such email or found Client.id equals client_id (patch client data)
        if (find == null || find.getId().equals(client_id)) {
            log.debug("Email={} is not in use or is in use for current client (id={})", email, client_id);
            return false;
        }
        log.debug("Email={} is already in use by client_id={} We try to use it for client_id={}", email, find.getId(), client_id);
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
        log.debug("Get service list marked which are bayed by client and which are not List={}",markedServices);
        return markedServices;
    }

    public Client findByEmail(String email) {
        Client client = clientRepo.findByEmail(email);
        if (client==null){
            log.error("No such client in db with email=",email);
            throw new IllegalArgumentException("No such client in db with email="+email);
        }
        log.debug("Find client by email={}", email);
        return client;
    }

    //return all client with role User
    @Transactional
    public List<Client> findAllClientWithUserRole() {
        Role role = roleRepo.getByName("ROLE_USER");
        List<Client> clients = clientRepo.findWithRole(role);
        log.debug("Find all client's in db sorted by role=ROLE_USER Client's={}", clients);
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
        log.debug("Store new service_id={} for client={} in db",serviceId,email);
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
        log.debug("Delete and, store change in db, service_id={} from client({}) service list",serviceId,email);
    }


    public boolean getActive(String email) {
        boolean active = findByEmail(email).isActive();
        log.debug("Check client ({}) for active {} ",email,active);
        return active;
    }

    public void adminUpdate(Long clientId, Client clientData) {
        Client currentClient = findById(clientId);
        currentClient.setFirstName(clientData.getFirstName());
        currentClient.setSecondName(clientData.getSecondName());
        currentClient.setEmail(clientData.getEmail());
        currentClient.setActive(clientData.isActive());
        update(currentClient);
        log.debug("Update client(id={}) data ({}) and store it in db",clientData);
    }

    public void updatePassword(Long clientId, String password) {
        Client client = findById(clientId);
        client.setPassword(passwordEncoder.encode(password));
        update(client);
        log.debug("Update client(id={}) password in db",clientId);
    }


    public List<Client> findAllClientsOrderedByCount() {
        sortCount = sortCount ? false : true;
        Role role = roleRepo.getByName("ROLE_USER");
        Sort.Direction direction = sortCount ? Sort.Direction.ASC : Sort.Direction.DESC;
        log.debug("Find all client's sorted by account.amount in direction ascending = {}",direction.isAscending());
        return clientRepo.findWithRoleOrdered(role, Sort.by(direction, "account.amount"));
    }
}
