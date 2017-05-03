package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityStatus entity.
 */
@SuppressWarnings("unused")
public interface FacilityStatusRepository extends JpaRepository<FacilityStatus,Long> {

}
