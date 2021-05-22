package org.callservice.models;

import javax.validation.constraints.DecimalMin;

public class Payment {

    Long id;

    @DecimalMin(value = "0.0", inclusive = false)
    double amount;

    public Payment() {
    }

    public Payment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
