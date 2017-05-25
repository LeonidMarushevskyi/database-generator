package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.CountyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity County and its DTO CountyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountyMapper extends EntityMapper <CountyDTO, County> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default County fromId(Long id) {
        if (id == null) {
            return null;
        }
        County county = new County();
        county.setId(id);
        return county;
    }
}
