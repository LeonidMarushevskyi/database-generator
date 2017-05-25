package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Facility and its DTO FacilityDTO.
 */
@Mapper(componentModel = "spring", uses = {AssignedWorkerMapper.class, DistrictOfficeMapper.class, FacilityTypeMapper.class, FacilityStatusMapper.class, CountyMapper.class, })
public interface FacilityMapper extends EntityMapper <FacilityDTO, Facility> {
    @Mapping(source = "assignedWorker.id", target = "assignedWorkerId")
    @Mapping(source = "districtOffice.id", target = "districtOfficeId")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "county.id", target = "countyId")
    FacilityDTO toDto(Facility facility); 
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "complaints", ignore = true)
    @Mapping(target = "inspections", ignore = true)
    @Mapping(source = "assignedWorkerId", target = "assignedWorker")
    @Mapping(source = "districtOfficeId", target = "districtOffice")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "countyId", target = "county")
    Facility toEntity(FacilityDTO facilityDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Facility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facility facility = new Facility();
        facility.setId(id);
        return facility;
    }
}
