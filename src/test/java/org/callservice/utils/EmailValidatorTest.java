package org.callservice.utils;

import org.callservice.configuration.LoggerConfig;
import org.callservice.configuration.TestConfiguration;
import org.callservice.models.Client;
import org.callservice.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;


import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfiguration.class, LoggerConfig.class}
)
class EmailValidatorTest {

    @Autowired
    private EmailValidator emailValidator;

    //mock
    @Autowired
    private ClientService clientService;

    private static final String email="test@email.com";
    private static final Client client=mock(Client.class);
    private static final Long clientId = new Long(1);

    @BeforeEach
    public void setup(){
        when(client.getEmail()).thenReturn(email);
        when(client.getId()).thenReturn(clientId);
        when(clientService.emailInUse(client.getId(), client.getEmail())).thenReturn(false);
        when(clientService.emailInUse(new Long(2), client.getEmail())).thenReturn(true);
    }

    @Test
    public void EmailValidationWithEmailInUseNoErrorRejection() {
        Errors errors = mock(Errors.class);

        emailValidator.validate(client, errors);
        //  errors.rejectValue("email", "", "This email is already in use");
        verify(errors, times(0)).rejectValue(any(), any(), any());
    }

    @Test
    public void EmailValidationWithNoEmailInUseRejectError(){
        Errors errors = mock(Errors.class);
        when(client.getId()).thenReturn(new Long(2));

        emailValidator.validate(client, errors);

        verify(errors, times(1)).rejectValue(eq("email"),any(),any());

    }
}