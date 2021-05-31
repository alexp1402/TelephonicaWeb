package org.callservice.models;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//Telephone Service
//name - telephone service name
//description - description of telephone service
//cost - price for that service


@Entity
@Table(name = "services")
public class TelephoneService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty()
    @Size(min = 2)
    private String name;

    @Column(name = "description")
    @NotEmpty
    @Size(min = 10)
    private String description;

    @Column(name = "cost")
    @Min(value = 0)
    private BigDecimal cost;

    @ManyToMany(mappedBy = "services")
    private Set<Client> clients = new HashSet<>();

    public TelephoneService() {
    }

    public TelephoneService(@NotEmpty() @Size(min = 2) String name, @NotEmpty @Size(min = 10) String description, @Min(value = 0) BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelephoneService that = (TelephoneService) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TelephoneService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
