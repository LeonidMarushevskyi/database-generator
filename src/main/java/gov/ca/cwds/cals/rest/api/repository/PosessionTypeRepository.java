package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.PosessionType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PosessionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PosessionTypeRepository extends JpaRepository<PosessionType,Long> {

}
