package org.callservice.models;

//3 basic filed "First name Last name Patronymic"
//1 email equal login
//1 password - set by admin

//1 can be blocked by admin -> field status - active/blocking
//1 list can use any services -> many to many ServiceList
//1 have bill -> now it is num field really it must be table

//1 Role (authorization&authentication) - ROLE_USER

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class User {

    private Long id;
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String firstName;
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String lastName;
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String patronymicName;

    @NotEmpty()
    @Email
    private String email;
    @NotEmpty()
    private String password;


    private Boolean activeStatus=new Boolean(true);
    private List<TelephoneService> services;
    private Double bill;

    private Role role;

    public User() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
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

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public List<TelephoneService> getServices() {
        return services;
    }

    public void setServices(List<TelephoneService> services) {
        this.services = services;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
