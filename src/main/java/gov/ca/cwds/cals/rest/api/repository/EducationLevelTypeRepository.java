package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.EducationLevelType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EducationLevelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationLevelTypeRepository extends JpaRepository<EducationLevelType,Long> {

}
