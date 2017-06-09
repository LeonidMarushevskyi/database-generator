package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.SiblingGroupType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SiblingGroupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiblingGroupTypeRepository extends JpaRepository<SiblingGroupType,Long> {

}
