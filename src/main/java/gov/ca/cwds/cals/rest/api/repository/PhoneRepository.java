package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Phone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Phone entity.
 */
@SuppressWarnings("unused")
public interface PhoneRepository extends JpaRepository<Phone,Long> {

}
