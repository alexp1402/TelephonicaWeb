package org.callservice.repositories;

import org.callservice.models.TelephoneService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneServiceRepo extends JpaRepository<TelephoneService, Long> {

}
