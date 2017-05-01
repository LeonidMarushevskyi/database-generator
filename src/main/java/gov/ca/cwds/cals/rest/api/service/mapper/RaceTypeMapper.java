package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.RaceTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RaceType and its DTO RaceTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaceTypeMapper {

    RaceTypeDTO raceTypeToRaceTypeDTO(RaceType raceType);

    List<RaceTypeDTO> raceTypesToRaceTypeDTOs(List<RaceType> raceTypes);

    RaceType raceTypeDTOToRaceType(RaceTypeDTO raceTypeDTO);

    List<RaceType> raceTypeDTOsToRaceTypes(List<RaceTypeDTO> raceTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default RaceType raceTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        RaceType raceType = new RaceType();
        raceType.setId(id);
        return raceType;
    }
    

}
