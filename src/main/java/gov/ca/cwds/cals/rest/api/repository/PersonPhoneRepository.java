package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonPhone;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone,Long> {

}
