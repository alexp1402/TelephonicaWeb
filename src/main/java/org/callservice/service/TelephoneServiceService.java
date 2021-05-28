package org.callservice.service;

import org.callservice.models.TelephoneService;
import org.callservice.repositories.TelephoneServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelephoneServiceService {

    private TelephoneServiceRepo telephoneServiceRepo;

    @Autowired
    public TelephoneServiceService(TelephoneServiceRepo telephoneServiceRepo) {
        this.telephoneServiceRepo = telephoneServiceRepo;
    }

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

    public TelephoneService findById(Long id){
        Optional<TelephoneService> getService = telephoneServiceRepo.findById(id);
        if(!getService.isPresent())
            throw new IllegalArgumentException("There is no Telephone Service in Db with id=" + id);
        return getService.get();
    }




}
