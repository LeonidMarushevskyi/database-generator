package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.LanguageTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LanguageType and its DTO LanguageTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguageTypeMapper {

    LanguageTypeDTO languageTypeToLanguageTypeDTO(LanguageType languageType);

    List<LanguageTypeDTO> languageTypesToLanguageTypeDTOs(List<LanguageType> languageTypes);

    LanguageType languageTypeDTOToLanguageType(LanguageTypeDTO languageTypeDTO);

    List<LanguageType> languageTypeDTOsToLanguageTypes(List<LanguageTypeDTO> languageTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LanguageType languageTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        LanguageType languageType = new LanguageType();
        languageType.setId(id);
        return languageType;
    }
    

}
