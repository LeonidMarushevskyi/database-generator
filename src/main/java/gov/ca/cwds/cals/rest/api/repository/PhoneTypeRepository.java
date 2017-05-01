package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PhoneType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PhoneType entity.
 */
@SuppressWarnings("unused")
public interface PhoneTypeRepository extends JpaRepository<PhoneType,Long> {

}
