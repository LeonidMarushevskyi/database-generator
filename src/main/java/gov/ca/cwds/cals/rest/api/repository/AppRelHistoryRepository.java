package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.AppRelHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AppRelHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRelHistoryRepository extends JpaRepository<AppRelHistory,Long> {

}
