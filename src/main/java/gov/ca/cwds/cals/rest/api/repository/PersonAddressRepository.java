package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonAddress entity.
 */
@SuppressWarnings("unused")
public interface PersonAddressRepository extends JpaRepository<PersonAddress,Long> {

}
