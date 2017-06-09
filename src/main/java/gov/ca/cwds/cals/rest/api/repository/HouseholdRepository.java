package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.Household;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Household entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseholdRepository extends JpaRepository<Household,Long> {

    @Query("select distinct household from Household household left join fetch household.languages")
    List<Household> findAllWithEagerRelationships();

    @Query("select household from Household household left join fetch household.languages where household.id =:id")
    Household findOneWithEagerRelationships(@Param("id") Long id);

}
