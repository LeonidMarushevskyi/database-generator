package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonRace;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonRace entity.
 */
@SuppressWarnings("unused")
public interface PersonRaceRepository extends JpaRepository<PersonRace,Long> {

}
