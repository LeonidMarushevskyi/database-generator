package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityChildDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacilityChild and its DTO FacilityChildDTO.
 */
@Mapper(componentModel = "spring", uses = {FacilityMapper.class, PersonMapper.class, })
public interface FacilityChildMapper extends EntityMapper <FacilityChildDTO, FacilityChild> {
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "person.id", target = "personId")
    FacilityChildDTO toDto(FacilityChild facilityChild); 
    @Mapping(source = "facilityId", target = "facility")
    @Mapping(source = "personId", target = "person")
    FacilityChild toEntity(FacilityChildDTO facilityChildDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FacilityChild fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityChild facilityChild = new FacilityChild();
        facilityChild.setId(id);
        return facilityChild;
    }
}
