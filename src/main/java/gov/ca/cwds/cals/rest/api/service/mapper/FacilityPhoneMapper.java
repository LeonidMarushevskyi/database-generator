package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityPhoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacilityPhone and its DTO FacilityPhoneDTO.
 */
@Mapper(componentModel = "spring", uses = {FacilityMapper.class, PhoneMapper.class, PhoneTypeMapper.class, })
public interface FacilityPhoneMapper extends EntityMapper <FacilityPhoneDTO, FacilityPhone> {
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "phone.id", target = "phoneId")
    @Mapping(source = "type.id", target = "typeId")
    FacilityPhoneDTO toDto(FacilityPhone facilityPhone); 
    @Mapping(source = "facilityId", target = "facility")
    @Mapping(source = "phoneId", target = "phone")
    @Mapping(source = "typeId", target = "type")
    FacilityPhone toEntity(FacilityPhoneDTO facilityPhoneDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FacilityPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityPhone facilityPhone = new FacilityPhone();
        facilityPhone.setId(id);
        return facilityPhone;
    }
}
