package org.callservice.service;

import org.callservice.models.TelephoneService;
import org.callservice.repositories.TelephoneServiceRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelephoneServiceService {

    private TelephoneServiceRepo telephoneServiceRepo;
    private Logger log;

    @Autowired
    public TelephoneServiceService(TelephoneServiceRepo telephoneServiceRepo, Logger log) {
        this.telephoneServiceRepo = telephoneServiceRepo;
        this.log = log;
    }

    public void save(TelephoneService tService) {
        //here can be validation by unique service name
        telephoneServiceRepo.save(tService);
        log.debug("Store telephone service ({}) in db", tService);
    }

    public void update(TelephoneService service) {
        telephoneServiceRepo.save(service);
        log.debug("Update existed service ({}) in db", service);
    }

    public Object findAll() {
        log.debug("Get all services from db");
        return telephoneServiceRepo.findAll();
    }

    public TelephoneService findById(Long id){
        Optional<TelephoneService> getService = telephoneServiceRepo.findById(id);
        if(!getService.isPresent()){
            log.error("No such telephone service (id={}) in db",id);
            throw new IllegalArgumentException("There is no Telephone Service in Db with id=" + id);
        }
        log.debug("Get service (id={}) from db",id);
        return getService.get();
    }
}
