package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.RaceType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RaceType entity.
 */
@SuppressWarnings("unused")
public interface RaceTypeRepository extends JpaRepository<RaceType,Long> {

}
