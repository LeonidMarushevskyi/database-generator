package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.RelationshipType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RelationshipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType,Long> {

}
