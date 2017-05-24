package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FacilityStatus and its DTO FacilityStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacilityStatusMapper {

    FacilityStatusDTO facilityStatusToFacilityStatusDTO(FacilityStatus facilityStatus);

    List<FacilityStatusDTO> facilityStatusesToFacilityStatusDTOs(List<FacilityStatus> facilityStatuses);

    FacilityStatus facilityStatusDTOToFacilityStatus(FacilityStatusDTO facilityStatusDTO);

    List<FacilityStatus> facilityStatusDTOsToFacilityStatuses(List<FacilityStatusDTO> facilityStatusDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FacilityStatus facilityStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setId(id);
        return facilityStatus;
    }
    

}
