package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.DeterminedChild;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeterminedChild entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeterminedChildRepository extends JpaRepository<DeterminedChild,Long> {

}
