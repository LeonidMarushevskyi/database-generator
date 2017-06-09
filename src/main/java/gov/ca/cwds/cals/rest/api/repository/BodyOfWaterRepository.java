package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.BodyOfWater;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BodyOfWater entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyOfWaterRepository extends JpaRepository<BodyOfWater,Long> {

}
