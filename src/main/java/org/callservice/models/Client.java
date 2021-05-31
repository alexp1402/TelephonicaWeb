package org.callservice.models;

//3 basic field "First name Last name Patronymic"
//1 email equal login
//1 password - set by admin

//1 can be blocked by admin -> field status - active/blocking
//1 list can use any services -> many to many ServiceList
//1 have bill -> now it is num field really it must be table

//1 Role (authorization&authentication) - ROLE_USER

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String firstName;

    @Column(name = "second_name")
    @NotEmpty()
    @Size(min = 2, max = 30)
    private String secondName;

    @Column(name = "email")
    @NotEmpty()
    @Email
    private String email;

    @Column(name = "password")
    @NotEmpty()
    @Size(min = 3)
    private String password;

    @Column(name = "active")
    private boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToMany
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    //cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH}
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bayed_service",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<TelephoneService> services = new HashSet<>();

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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Client(@NotEmpty() @Size(min = 2, max = 30) String firstName, @NotEmpty() @Size(min = 2, max = 30) String secondName, @NotEmpty() @Email String email, @NotEmpty() @Size(min = 3) String password, boolean active, Account account, Set<Role> role, Set<TelephoneService> services) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.account = account;
        this.role = role;
        this.services = services;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Set<TelephoneService> getServices() {
        return services;
    }

    public void setServices(Set<TelephoneService> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
