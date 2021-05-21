package org.callservice.utils;

import org.callservice.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//Validate email for unique

@Component
public class EmailValidator implements Validator {

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

//        if (ClientRepo.show(person.getEmail()).isPresent()) {
//            // поле, код ошибки, сообщение ошибки
//            errors.rejectValue("email", "", "This email is already in use");
//        }
////////        errors.rejectValue("email","","This email is already in use");
    }
}
