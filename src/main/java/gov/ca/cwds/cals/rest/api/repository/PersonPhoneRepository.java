package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PersonPhone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonPhone entity.
 */
@SuppressWarnings("unused")
public interface PersonPhoneRepository extends JpaRepository<PersonPhone,Long> {

}
