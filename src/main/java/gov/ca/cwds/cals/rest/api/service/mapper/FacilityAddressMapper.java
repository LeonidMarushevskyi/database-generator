package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacilityAddress and its DTO FacilityAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {FacilityMapper.class, AddressMapper.class, AddressTypeMapper.class, })
public interface FacilityAddressMapper extends EntityMapper <FacilityAddressDTO, FacilityAddress> {
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "type.id", target = "typeId")
    FacilityAddressDTO toDto(FacilityAddress facilityAddress); 
    @Mapping(source = "facilityId", target = "facility")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "typeId", target = "type")
    FacilityAddress toEntity(FacilityAddressDTO facilityAddressDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FacilityAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityAddress facilityAddress = new FacilityAddress();
        facilityAddress.setId(id);
        return facilityAddress;
    }
}
