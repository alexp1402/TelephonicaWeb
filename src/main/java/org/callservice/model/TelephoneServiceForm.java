package org.callservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class TelephoneServiceForm{

    private Long id;
    @NotNull(message = "Must be not null")
    @Size(min=2, message = "Must be greater then 2 characters")
    private String name;
    @NotNull(message = "Must be not null")
    @Size(min=2, message="Must be greater then 2 characters")
    private String description;
    @NotNull(message = "Must be not null and greater or equal 0")
    @Min(value=0, message="Must be greater than 0")
    private Double cost;


    private MessageSource getMsg;

    @Autowired
    public TelephoneServiceForm(MessageSource getMsg) {
        this.getMsg = getMsg;
    }

    public TelephoneServiceForm() {
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
