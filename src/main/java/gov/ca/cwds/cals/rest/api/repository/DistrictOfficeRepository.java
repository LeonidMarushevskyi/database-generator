package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.DistrictOffice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DistrictOffice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictOfficeRepository extends JpaRepository<DistrictOffice,Long> {

}
