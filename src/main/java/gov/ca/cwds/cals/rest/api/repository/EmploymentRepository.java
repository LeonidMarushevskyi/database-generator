package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Employment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Employment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentRepository extends JpaRepository<Employment,Long> {

}
