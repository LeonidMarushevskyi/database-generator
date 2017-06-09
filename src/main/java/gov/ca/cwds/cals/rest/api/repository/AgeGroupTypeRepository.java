package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.AgeGroupType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AgeGroupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgeGroupTypeRepository extends JpaRepository<AgeGroupType,Long> {

}
