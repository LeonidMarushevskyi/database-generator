package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacilityStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityStatusRepository extends JpaRepository<FacilityStatus,Long> {

}
