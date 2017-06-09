package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.LicensureHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LicensureHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicensureHistoryRepository extends JpaRepository<LicensureHistory,Long> {

}
