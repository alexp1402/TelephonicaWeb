package org.callservice.service;


import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.repositories.ClientRepo;
import org.callservice.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserInitService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private RoleRepo roleRepo;

    public UserInitService() {
    }

    @Transactional
    public void initRolesAdmin() {
        //init two roles in db ADMIN and USER
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepo.save(roleUser);
        roleRepo.save(roleAdmin);
        //init admin with login admin@admin and password admin in DB
        Set<Role> rSet = new HashSet<>();
        rSet.add(roleAdmin);
        Client client = new Client("admin", "admin", "admin@admin.com",
                "admin",
                false, null, rSet, null);
        clientService.save(client);

//        create user

        client = new Client("Alex", "Pleskachev", "leshii85@gmail.com",
                "12345", true, null, null, null);

        clientService.save(client);
    }
}

