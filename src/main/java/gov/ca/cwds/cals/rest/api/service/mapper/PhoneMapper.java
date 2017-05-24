package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PhoneDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Phone and its DTO PhoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhoneMapper {

    PhoneDTO phoneToPhoneDTO(Phone phone);

    List<PhoneDTO> phonesToPhoneDTOs(List<Phone> phones);

    Phone phoneDTOToPhone(PhoneDTO phoneDTO);

    List<Phone> phoneDTOsToPhones(List<PhoneDTO> phoneDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Phone phoneFromId(Long id) {
        if (id == null) {
            return null;
        }
        Phone phone = new Phone();
        phone.setId(id);
        return phone;
    }
    

}
