package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.DeficiencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Deficiency and its DTO DeficiencyDTO.
 */
@Mapper(componentModel = "spring", uses = {InspectionMapper.class, })
public interface DeficiencyMapper extends EntityMapper <DeficiencyDTO, Deficiency> {
    @Mapping(source = "inspection.id", target = "inspectionId")
    DeficiencyDTO toDto(Deficiency deficiency); 
    @Mapping(source = "inspectionId", target = "inspection")
    Deficiency toEntity(DeficiencyDTO deficiencyDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Deficiency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deficiency deficiency = new Deficiency();
        deficiency.setId(id);
        return deficiency;
    }
}
