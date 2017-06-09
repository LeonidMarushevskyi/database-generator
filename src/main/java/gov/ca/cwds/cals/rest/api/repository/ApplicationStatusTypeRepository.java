package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.ApplicationStatusType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ApplicationStatusType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationStatusTypeRepository extends JpaRepository<ApplicationStatusType,Long> {

}
