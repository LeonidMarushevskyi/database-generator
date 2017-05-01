package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityPhone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityPhone entity.
 */
@SuppressWarnings("unused")
public interface FacilityPhoneRepository extends JpaRepository<FacilityPhone,Long> {

}
