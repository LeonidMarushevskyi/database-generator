package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.EthnicityTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EthnicityType and its DTO EthnicityTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EthnicityTypeMapper {

    EthnicityTypeDTO ethnicityTypeToEthnicityTypeDTO(EthnicityType ethnicityType);

    List<EthnicityTypeDTO> ethnicityTypesToEthnicityTypeDTOs(List<EthnicityType> ethnicityTypes);

    EthnicityType ethnicityTypeDTOToEthnicityType(EthnicityTypeDTO ethnicityTypeDTO);

    List<EthnicityType> ethnicityTypeDTOsToEthnicityTypes(List<EthnicityTypeDTO> ethnicityTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default EthnicityType ethnicityTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        EthnicityType ethnicityType = new EthnicityType();
        ethnicityType.setId(id);
        return ethnicityType;
    }
    

}
