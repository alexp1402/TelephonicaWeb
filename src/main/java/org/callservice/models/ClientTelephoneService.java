package org.callservice.models;

//like wrapper for client service page
public class ClientTelephoneService{

    private Long id;

    private TelephoneService tService;
    private boolean bayed;

    public ClientTelephoneService(TelephoneService tService, boolean bayed) {
        this.tService = tService;
        this.bayed = bayed;
    }

    public TelephoneService getService() {
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
}
