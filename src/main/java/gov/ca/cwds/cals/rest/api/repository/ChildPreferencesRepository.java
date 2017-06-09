package gov.ca.cwds.cals.rest.api.repository;

import gov.ca.cwds.cals.rest.api.domain.ChildPreferences;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ChildPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildPreferencesRepository extends JpaRepository<ChildPreferences,Long> {

    @Query("select distinct child_preferences from ChildPreferences child_preferences left join fetch child_preferences.ageGroupTypes left join fetch child_preferences.siblingGroupTypes")
    List<ChildPreferences> findAllWithEagerRelationships();

    @Query("select child_preferences from ChildPreferences child_preferences left join fetch child_preferences.ageGroupTypes left join fetch child_preferences.siblingGroupTypes where child_preferences.id =:id")
    ChildPreferences findOneWithEagerRelationships(@Param("id") Long id);

}
