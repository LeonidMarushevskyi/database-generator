package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.StateType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateTypeRepository extends JpaRepository<StateType,Long> {

}
