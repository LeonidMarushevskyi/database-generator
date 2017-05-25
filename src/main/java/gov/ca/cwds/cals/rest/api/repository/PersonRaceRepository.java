package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonRace;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonRace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRaceRepository extends JpaRepository<PersonRace,Long> {

}
