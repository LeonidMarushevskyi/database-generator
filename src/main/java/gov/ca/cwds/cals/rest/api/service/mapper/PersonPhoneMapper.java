package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonPhoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonPhone and its DTO PersonPhoneDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, PhoneMapper.class, PhoneTypeMapper.class, })
public interface PersonPhoneMapper extends EntityMapper <PersonPhoneDTO, PersonPhone> {
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "phone.id", target = "phoneId")
    @Mapping(source = "type.id", target = "typeId")
    PersonPhoneDTO toDto(PersonPhone personPhone); 
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "phoneId", target = "phone")
    @Mapping(source = "typeId", target = "type")
    PersonPhone toEntity(PersonPhoneDTO personPhoneDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PersonPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonPhone personPhone = new PersonPhone();
        personPhone.setId(id);
        return personPhone;
    }
}
