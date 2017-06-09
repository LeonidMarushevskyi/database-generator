package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PhoneNumberType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PhoneNumberType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneNumberTypeRepository extends JpaRepository<PhoneNumberType,Long> {

}
