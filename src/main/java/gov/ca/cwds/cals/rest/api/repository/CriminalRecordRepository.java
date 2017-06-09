package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.CriminalRecord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CriminalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriminalRecordRepository extends JpaRepository<CriminalRecord,Long> {

}
