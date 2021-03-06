package org.callservice.utils;

import org.callservice.models.Client;
import org.callservice.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//Validate email for unique
@Component
public class EmailValidator implements Validator {
    @Autowired
    private Logger log;

    @Autowired
    ClientService clientService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        if (clientService.emailInUse(client.getId(),client.getEmail())) {
            // field | error number | message
            log.debug("Wrong email for client id={} email {} already in use", client.getId(), client.getEmail());
            errors.rejectValue("email", "", "This email is already in use");
        }else{
            log.debug("Email {} got validation for client id={}",client.getEmail(), client.getId());
        }

    }
}
