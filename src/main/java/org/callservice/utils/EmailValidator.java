package org.callservice.utils;

import org.callservice.models.Client;
import org.callservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//Validate email for unique

@Component
public class EmailValidator implements Validator {

    @Autowired
    ClientService clientService;

    //private ClientRepo client;

//    @Autowired
//    public EmailValidator(Client client) {
//        this.client = client;
//    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;
        ;
        if (clientService.emailInUse(client.getEmail())) {
            // field | error number | message
            errors.rejectValue("email", "", "This email is already in use");
        }
    }
}
