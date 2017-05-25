package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.ClearedPOCDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClearedPOC and its DTO ClearedPOCDTO.
 */
@Mapper(componentModel = "spring", uses = {InspectionMapper.class, })
public interface ClearedPOCMapper extends EntityMapper <ClearedPOCDTO, ClearedPOC> {
    @Mapping(source = "inspection.id", target = "inspectionId")
    ClearedPOCDTO toDto(ClearedPOC clearedPOC); 
    @Mapping(source = "inspectionId", target = "inspection")
    ClearedPOC toEntity(ClearedPOCDTO clearedPOCDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ClearedPOC fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClearedPOC clearedPOC = new ClearedPOC();
        clearedPOC.setId(id);
        return clearedPOC;
    }
}
