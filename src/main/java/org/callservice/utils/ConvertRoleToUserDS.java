package org.callservice.utils;

import org.callservice.models.Client;
import org.callservice.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

//implements UserDetailsService to compare exist user in db or not and return UserDetailService for Security
@Component
public class ConvertRoleToUserDS implements UserDetailsService {

    @Autowired
    ClientRepo clientRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepo.findByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException(String.format("No such email '%s' in DB", email));
        }

        return new org.springframework.security.core.userdetails.User(client.getEmail(), client.getPassword(),
                client.getRole().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList())
        );
    }
}
