package org.callservice.repositories;

import org.callservice.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client,Long> {

    Client findByEmail(String email);

//    @Query(value = "select clients.id from clients where clients.email = :email", nativeQuery = true)
//    Long findByEmail(@Param("email") String email);


}
