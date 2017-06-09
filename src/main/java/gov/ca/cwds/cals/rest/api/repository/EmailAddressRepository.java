package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.EmailAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmailAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailAddressRepository extends JpaRepository<EmailAddress,Long> {

}
