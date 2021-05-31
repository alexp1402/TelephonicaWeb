package org.callservice.configuration;

import org.callservice.repositories.AccountRepo;
import org.callservice.service.AccountService;
import org.callservice.service.ClientService;
import org.callservice.utils.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {

    @Bean
    public EmailValidator emailValidator(){
        return new EmailValidator();
    }

    @Bean
    public ClientService clientService(){
        return mock(ClientService.class);
    }

    @Bean
    public AccountRepo accountRepo(){ return mock(AccountRepo.class); }

    @Bean
    public AccountService accountService(){return new AccountService();}

}
