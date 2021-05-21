package org.callservice.models;

//3 basic filed "First name Last name Patronymic"
//1 email equal login
//1 password - set by admin

//1 can be blocked by admin -> field status - active/blocking
//1 list can use any services -> many to many ServiceList
//1 have bill -> now it is num field really it must be table

//1 Role (authorization&authentication) - ROLE_USER

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


public class Client {

    private Long id;
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String firstName;
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String secondName;


    @NotEmpty()
    @Email
    private String email;
    @NotEmpty()
    @Size(min = 3)
    private String password;


    private boolean active;

    private Set<Long> serviceId;

    private Long accountId;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role="USER";


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getServiceId() {
        return serviceId;
    }

    public void setServiceId(Set<Long> serviceId) {
        this.serviceId = serviceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
