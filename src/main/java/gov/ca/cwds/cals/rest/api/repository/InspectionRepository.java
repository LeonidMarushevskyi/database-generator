package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Inspection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inspection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRepository extends JpaRepository<Inspection,Long> {

}
