package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.LanguageType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LanguageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguageTypeRepository extends JpaRepository<LanguageType,Long> {

}
