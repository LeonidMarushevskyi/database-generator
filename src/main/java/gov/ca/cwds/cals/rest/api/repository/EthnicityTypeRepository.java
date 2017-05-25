package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.EthnicityType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EthnicityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EthnicityTypeRepository extends JpaRepository<EthnicityType,Long> {

}
