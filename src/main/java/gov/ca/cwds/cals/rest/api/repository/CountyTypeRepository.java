package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.CountyType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CountyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountyTypeRepository extends JpaRepository<CountyType,Long> {

}
