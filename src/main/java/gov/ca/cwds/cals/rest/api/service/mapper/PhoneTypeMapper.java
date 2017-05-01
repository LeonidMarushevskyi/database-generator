package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PhoneTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PhoneType and its DTO PhoneTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhoneTypeMapper {

    PhoneTypeDTO phoneTypeToPhoneTypeDTO(PhoneType phoneType);

    List<PhoneTypeDTO> phoneTypesToPhoneTypeDTOs(List<PhoneType> phoneTypes);

    PhoneType phoneTypeDTOToPhoneType(PhoneTypeDTO phoneTypeDTO);

    List<PhoneType> phoneTypeDTOsToPhoneTypes(List<PhoneTypeDTO> phoneTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PhoneType phoneTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PhoneType phoneType = new PhoneType();
        phoneType.setId(id);
        return phoneType;
    }
    

}
