package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.RaceType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RaceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaceTypeRepository extends JpaRepository<RaceType,Long> {

}
