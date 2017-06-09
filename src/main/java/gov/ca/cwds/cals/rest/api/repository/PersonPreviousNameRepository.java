package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonPreviousName;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonPreviousName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonPreviousNameRepository extends JpaRepository<PersonPreviousName,Long> {

}
