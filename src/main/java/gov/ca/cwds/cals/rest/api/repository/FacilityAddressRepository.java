package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacilityAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityAddressRepository extends JpaRepository<FacilityAddress,Long> {

}
