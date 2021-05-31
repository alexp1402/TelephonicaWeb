package org.callservice.service;

import org.callservice.configuration.LoggerConfig;
import org.callservice.configuration.TestConfiguration;
import org.callservice.models.Account;
import org.callservice.models.Client;
import org.callservice.models.Payment;
import org.callservice.repositories.AccountRepo;
import org.callservice.utils.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfiguration.class, LoggerConfig.class}
)
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    //mock
    @Autowired
    private AccountRepo accountRepo;

    //@Autowired
    private Payment payment = mock(Payment.class);
    private Client client = mock(Client.class);
    private Account account = mock(Account.class);

    private Long id= new Long("1");

    @BeforeEach
    public void setup(){
       // when(accountRepo.getById(id)).thenReturn(null);
        when(account.getAmount()).thenReturn(new BigDecimal(5));
        when(client.getAccount()).thenReturn(account);
        //when(account.setAmount(any(BigDecimal.class))).thenCallRealMethod(BigDecimal.class.getDeclaredMethod("add", BigDecimal()))

//        when(client.getId()).thenReturn(clientId);
//        when(clientService.emailInUse(client.getId(), client.getEmail())).thenReturn(false);
//        when(clientService.emailInUse(new Long(2), client.getEmail())).thenReturn(true);
    }

    @Test
    public void addPaymentTest(){
        accountService.addPayment(payment,client);

    }

//    public void update(Account account){
//        accountRepo.save(account);
//    }


}