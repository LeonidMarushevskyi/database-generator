package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonEthnicityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonEthnicity and its DTO PersonEthnicityDTO.
 */
@Mapper(componentModel = "spring", uses = {EthnicityTypeMapper.class, })
public interface PersonEthnicityMapper extends EntityMapper <PersonEthnicityDTO, PersonEthnicity> {
    @Mapping(source = "ethnicity.id", target = "ethnicityId")
    @Mapping(source = "subEthnicity.id", target = "subEthnicityId")
    PersonEthnicityDTO toDto(PersonEthnicity personEthnicity); 
    @Mapping(source = "ethnicityId", target = "ethnicity")
    @Mapping(source = "subEthnicityId", target = "subEthnicity")
    PersonEthnicity toEntity(PersonEthnicityDTO personEthnicityDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PersonEthnicity fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonEthnicity personEthnicity = new PersonEthnicity();
        personEthnicity.setId(id);
        return personEthnicity;
    }
}
