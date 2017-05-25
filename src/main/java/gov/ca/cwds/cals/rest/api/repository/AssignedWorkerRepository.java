package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.AssignedWorker;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AssignedWorker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignedWorkerRepository extends JpaRepository<AssignedWorker,Long> {

}
