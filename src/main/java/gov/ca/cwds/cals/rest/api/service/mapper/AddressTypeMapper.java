package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.AddressTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddressType and its DTO AddressTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressTypeMapper extends EntityMapper <AddressTypeDTO, AddressType> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default AddressType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressType addressType = new AddressType();
        addressType.setId(id);
        return addressType;
    }
}
