package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.HouseholdAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HouseholdAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseholdAddressRepository extends JpaRepository<HouseholdAddress,Long> {

}
