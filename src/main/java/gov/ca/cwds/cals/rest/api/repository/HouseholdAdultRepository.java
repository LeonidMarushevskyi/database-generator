package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.HouseholdAdult;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the HouseholdAdult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseholdAdultRepository extends JpaRepository<HouseholdAdult,Long> {

    @Query("select distinct household_adult from HouseholdAdult household_adult left join fetch household_adult.otherStates")
    List<HouseholdAdult> findAllWithEagerRelationships();

    @Query("select household_adult from HouseholdAdult household_adult left join fetch household_adult.otherStates where household_adult.id =:id")
    HouseholdAdult findOneWithEagerRelationships(@Param("id") Long id);

}
