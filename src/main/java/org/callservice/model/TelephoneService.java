package org.callservice.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TelephoneService {

    private Long id;
    @NotEmpty()
    @Size(min = 2)
    private String name;
    @NotEmpty
    @Size(min = 2)
    private String description;
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
