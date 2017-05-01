package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Facility and its DTO FacilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacilityMapper {

    FacilityDTO facilityToFacilityDTO(Facility facility);

    List<FacilityDTO> facilitiesToFacilityDTOs(List<Facility> facilities);

    @Mapping(target = "addresses", ignore = true)
    Facility facilityDTOToFacility(FacilityDTO facilityDTO);

    List<Facility> facilityDTOsToFacilities(List<FacilityDTO> facilityDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Facility facilityFromId(Long id) {
        if (id == null) {
            return null;
        }
        Facility facility = new Facility();
        facility.setId(id);
        return facility;
    }
    

}
