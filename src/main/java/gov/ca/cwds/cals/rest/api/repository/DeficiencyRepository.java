package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Deficiency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Deficiency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeficiencyRepository extends JpaRepository<Deficiency,Long> {

}
