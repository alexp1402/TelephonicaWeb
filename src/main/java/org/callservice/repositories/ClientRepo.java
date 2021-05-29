package org.callservice.repositories;

import org.callservice.models.Client;
import org.callservice.models.Role;
import org.callservice.models.TelephoneService;
import org.springframework.data.domain.Sort;
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

    @Query("SELECT c FROM Client c WHERE :arole MEMBER OF c.role")
    List<Client> findWithRole(@Param("arole") Role role);

    //ORDER BY c.account.amount :direction"
    @Query("SELECT c FROM Client c WHERE :arole MEMBER OF c.role")
    List<Client> findWithRoleOrdered(@Param("arole") Role role, Sort sort);
}
