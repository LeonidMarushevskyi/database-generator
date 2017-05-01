package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonLanguage entity.
 */
@SuppressWarnings("unused")
public interface PersonLanguageRepository extends JpaRepository<PersonLanguage,Long> {

}
