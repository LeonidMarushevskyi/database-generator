package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress,Long> {

}
