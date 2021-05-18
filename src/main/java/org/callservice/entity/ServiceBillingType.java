package org.callservice.entity;

public enum ServiceBillingType {
    ONETIME("One time service"), PERIODICAL("Periodical service");

    private final String displayValue;

    private ServiceBillingType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
