package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PhoneType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PhoneType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneTypeRepository extends JpaRepository<PhoneType,Long> {

}
