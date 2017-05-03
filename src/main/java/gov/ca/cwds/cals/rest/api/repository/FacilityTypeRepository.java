package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityType entity.
 */
@SuppressWarnings("unused")
public interface FacilityTypeRepository extends JpaRepository<FacilityType,Long> {

}
