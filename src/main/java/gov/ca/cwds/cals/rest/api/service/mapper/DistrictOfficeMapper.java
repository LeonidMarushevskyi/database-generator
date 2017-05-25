package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.DistrictOfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DistrictOffice and its DTO DistrictOfficeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictOfficeMapper extends EntityMapper <DistrictOfficeDTO, DistrictOffice> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default DistrictOffice fromId(Long id) {
        if (id == null) {
            return null;
        }
        DistrictOffice districtOffice = new DistrictOffice();
        districtOffice.setId(id);
        return districtOffice;
    }
}
