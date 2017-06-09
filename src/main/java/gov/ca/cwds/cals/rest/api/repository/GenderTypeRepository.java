package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.GenderType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GenderType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenderTypeRepository extends JpaRepository<GenderType,Long> {

}
