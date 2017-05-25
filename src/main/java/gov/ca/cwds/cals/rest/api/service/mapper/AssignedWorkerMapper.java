package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.AssignedWorkerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AssignedWorker and its DTO AssignedWorkerDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, })
public interface AssignedWorkerMapper extends EntityMapper <AssignedWorkerDTO, AssignedWorker> {
    @Mapping(source = "person.id", target = "personId")
    AssignedWorkerDTO toDto(AssignedWorker assignedWorker); 
    @Mapping(source = "personId", target = "person")
    AssignedWorker toEntity(AssignedWorkerDTO assignedWorkerDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default AssignedWorker fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssignedWorker assignedWorker = new AssignedWorker();
        assignedWorker.setId(id);
        return assignedWorker;
    }
}
