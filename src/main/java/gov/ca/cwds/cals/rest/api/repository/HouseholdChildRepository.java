package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.HouseholdChild;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HouseholdChild entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseholdChildRepository extends JpaRepository<HouseholdChild,Long> {

}
