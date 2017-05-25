package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.InspectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Inspection and its DTO InspectionDTO.
 */
@Mapper(componentModel = "spring", uses = {FacilityMapper.class, })
public interface InspectionMapper extends EntityMapper <InspectionDTO, Inspection> {
    @Mapping(source = "facility.id", target = "facilityId")
    InspectionDTO toDto(Inspection inspection); 
    @Mapping(source = "facilityId", target = "facility")
    @Mapping(target = "deficiencies", ignore = true)
    @Mapping(target = "clearedPOCS", ignore = true)
    Inspection toEntity(InspectionDTO inspectionDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Inspection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inspection inspection = new Inspection();
        inspection.setId(id);
        return inspection;
    }
}
