package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonAddress and its DTO PersonAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, AddressMapper.class, AddressTypeMapper.class, })
public interface PersonAddressMapper extends EntityMapper <PersonAddressDTO, PersonAddress> {
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "race.id", target = "raceId")
    @Mapping(source = "type.id", target = "typeId")
    PersonAddressDTO toDto(PersonAddress personAddress); 
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "raceId", target = "race")
    @Mapping(source = "typeId", target = "type")
    PersonAddress toEntity(PersonAddressDTO personAddressDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PersonAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonAddress personAddress = new PersonAddress();
        personAddress.setId(id);
        return personAddress;
    }
}
