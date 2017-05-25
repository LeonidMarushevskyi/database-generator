package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Complaint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Complaint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {

}
