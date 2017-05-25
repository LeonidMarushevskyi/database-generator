package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonRaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonRace and its DTO PersonRaceDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, RaceTypeMapper.class, })
public interface PersonRaceMapper extends EntityMapper <PersonRaceDTO, PersonRace> {
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "race.id", target = "raceId")
    PersonRaceDTO toDto(PersonRace personRace); 
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "raceId", target = "race")
    PersonRace toEntity(PersonRaceDTO personRaceDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PersonRace fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonRace personRace = new PersonRace();
        personRace.setId(id);
        return personRace;
    }
}
