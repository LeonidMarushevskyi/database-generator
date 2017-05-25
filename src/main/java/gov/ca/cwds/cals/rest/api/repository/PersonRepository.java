package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Person;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

}
