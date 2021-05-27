package org.callservice.service;

import org.callservice.models.TelephoneService;
import org.callservice.repositories.TelephoneServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelephoneServiceService {
    @Autowired
    TelephoneServiceRepo telephoneServiceRepo;


    public void save(TelephoneService tService) {
        //here can be validation by unique service name
        telephoneServiceRepo.save(tService);
    }

    public void update(TelephoneService service) {
        telephoneServiceRepo.save(service);
    }

    public Object findAll() {
        return telephoneServiceRepo.findAll();
    }

    public TelephoneService getById(Long id) {
        TelephoneService tService = telephoneServiceRepo.getById(id);
        if (tService == null)
            throw new IllegalArgumentException("There is no Telephone Service in Db with id=" + id);
        return tService;
    }


}
