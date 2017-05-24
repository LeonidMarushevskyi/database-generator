package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.AddressType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AddressType entity.
 */
@SuppressWarnings("unused")
public interface AddressTypeRepository extends JpaRepository<AddressType,Long> {

}
