package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.AddressType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AddressType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType,Long> {

}
