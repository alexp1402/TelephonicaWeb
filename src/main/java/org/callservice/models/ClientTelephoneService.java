package org.callservice.models;

//like wrapper for client service page
public class ClientTelephoneService{

    private TelephoneService service;
    private boolean active;

    public ClientTelephoneService(TelephoneService service, boolean active) {
        this.service = service;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
