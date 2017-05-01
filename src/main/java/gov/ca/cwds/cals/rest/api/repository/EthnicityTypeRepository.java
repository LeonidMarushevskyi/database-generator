package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.EthnicityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EthnicityType entity.
 */
@SuppressWarnings("unused")
public interface EthnicityTypeRepository extends JpaRepository<EthnicityType,Long> {

}
