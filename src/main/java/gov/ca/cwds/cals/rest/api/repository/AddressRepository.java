package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Address;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}
