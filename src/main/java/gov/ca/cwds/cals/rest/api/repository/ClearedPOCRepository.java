package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.ClearedPOC;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClearedPOC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClearedPOCRepository extends JpaRepository<ClearedPOC,Long> {

}
