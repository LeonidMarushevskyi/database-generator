package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonEthnicity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonEthnicity entity.
 */
@SuppressWarnings("unused")
public interface PersonEthnicityRepository extends JpaRepository<PersonEthnicity,Long> {

}
