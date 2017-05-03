package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.DistrictOfficeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DistrictOffice and its DTO DistrictOfficeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictOfficeMapper {

    DistrictOfficeDTO districtOfficeToDistrictOfficeDTO(DistrictOffice districtOffice);

    List<DistrictOfficeDTO> districtOfficesToDistrictOfficeDTOs(List<DistrictOffice> districtOffices);

    DistrictOffice districtOfficeDTOToDistrictOffice(DistrictOfficeDTO districtOfficeDTO);

    List<DistrictOffice> districtOfficeDTOsToDistrictOffices(List<DistrictOfficeDTO> districtOfficeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default DistrictOffice districtOfficeFromId(Long id) {
        if (id == null) {
            return null;
        }
        DistrictOffice districtOffice = new DistrictOffice();
        districtOffice.setId(id);
        return districtOffice;
    }
    

}
