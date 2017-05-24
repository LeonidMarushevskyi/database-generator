package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Facility;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Facility entity.
 */
@SuppressWarnings("unused")
public interface FacilityRepository extends JpaRepository<Facility,Long> {

}
