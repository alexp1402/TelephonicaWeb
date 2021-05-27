package org.callservice.repositories;

import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepo extends JpaRepository<Client,Long> {

    Client findByEmail(String email);
    //join fetch c.role where c.role.name='USER_ROLE'
//    @Query(value="select * from clients c where c.id in (select cr.client_id from clients_roles cr where cr.role_id in (select r.id from roles r where r.name = :role))",
//            nativeQuery = true)
    //WHERE :role MEMBER OF c.role
    @Query("SELECT c FROM Client c WHERE :arole MEMBER OF c.role")
    List<Client> findWithRole(@Param("arole") Role role);

//    @Query("SELECT c.services.id FROM Client c WHERE :arole MEMBER OF c.role")
//    Long findServiceId(@Param("arole") Role role);
}
