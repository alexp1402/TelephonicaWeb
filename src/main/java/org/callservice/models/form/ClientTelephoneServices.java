package org.callservice.models.form;

import org.callservice.models.TelephoneService;

//like wrapper for client service page
public class ClientTelephoneServices {

    private TelephoneService tService;
    private boolean bayed;

    public ClientTelephoneServices() {
    }

    public ClientTelephoneServices(TelephoneService tService, boolean bayed) {
        this.tService = tService;
        this.bayed = bayed;
    }

    public TelephoneService gettService() {
        return tService;
    }

    public void settService(TelephoneService tService) {
        this.tService = tService;
    }

    public boolean isBayed() {
        return bayed;
    }

    public void setBayed(boolean bayed) {
        this.bayed = bayed;
    }

    @Override
    public String toString() {
        return "ClientTelephoneServices{" +
                "TelephoneService=" + tService +
                ", bayed=" + bayed +
                '}';
    }
}
