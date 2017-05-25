package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.ComplaintDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Complaint and its DTO ComplaintDTO.
 */
@Mapper(componentModel = "spring", uses = {FacilityMapper.class, })
public interface ComplaintMapper extends EntityMapper <ComplaintDTO, Complaint> {
    @Mapping(source = "facility.id", target = "facilityId")
    ComplaintDTO toDto(Complaint complaint); 
    @Mapping(source = "facilityId", target = "facility")
    Complaint toEntity(ComplaintDTO complaintDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Complaint fromId(Long id) {
        if (id == null) {
            return null;
        }
        Complaint complaint = new Complaint();
        complaint.setId(id);
        return complaint;
    }
}
