package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacilityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType,Long> {

}
