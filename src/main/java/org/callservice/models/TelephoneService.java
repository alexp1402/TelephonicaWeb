package org.callservice.models;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Telephone Service
//name - telephone service name
//description - description of telephone service
//cost - price for that service


@Entity
@Table(name = "t_services")
public class TelephoneService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Double cost;

    public TelephoneService() {
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

}
