package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonEthnicityMapper.class, })
public interface PersonMapper extends EntityMapper <PersonDTO, Person> {
    @Mapping(source = "ethnicity.id", target = "ethnicityId")
    PersonDTO toDto(Person person); 
    @Mapping(source = "ethnicityId", target = "ethnicity")
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "races", ignore = true)
    Person toEntity(PersonDTO personDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
