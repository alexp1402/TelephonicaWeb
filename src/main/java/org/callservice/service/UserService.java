package org.callservice.service;


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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
//implements UserDetailsService to compare exist user in db or not and return UserDetailService for Security
public class UserService implements UserDetailsService {

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepo.findByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException(String.format("o such email '%s' in DB", email));
        }

        return new org.springframework.security.core.userdetails.User(client.getEmail(), client.getPassword(),
                client.getRole().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet())
        );
    }

    public void initRolesAdmin() {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepo.save(roleUser);
        roleRepo.save(roleAdmin);
        Set<Role> rSet = new HashSet<>();
        rSet.add(roleAdmin);
        Client client = new Client("admin", "admin", "admin@admin",
                "admin", false, null, rSet, null);
        clientService.save(client);
    }
}
