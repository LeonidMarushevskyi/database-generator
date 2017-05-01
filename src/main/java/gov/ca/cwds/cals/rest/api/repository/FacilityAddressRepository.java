package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.FacilityAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityAddress entity.
 */
@SuppressWarnings("unused")
public interface FacilityAddressRepository extends JpaRepository<FacilityAddress,Long> {

}
