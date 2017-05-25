package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacilityType and its DTO FacilityTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacilityTypeMapper extends EntityMapper <FacilityTypeDTO, FacilityType> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FacilityType fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityType facilityType = new FacilityType();
        facilityType.setId(id);
        return facilityType;
    }
}
