package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.RelationshipEventType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RelationshipEventType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipEventTypeRepository extends JpaRepository<RelationshipEventType,Long> {

}
