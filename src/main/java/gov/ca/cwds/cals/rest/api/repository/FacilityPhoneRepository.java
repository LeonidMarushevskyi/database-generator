package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityPhone;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacilityPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityPhoneRepository extends JpaRepository<FacilityPhone,Long> {

}
