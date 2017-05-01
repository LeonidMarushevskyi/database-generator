package gov.ca.cwds.cals.rest.api.service.mapper;

import gov.ca.cwds.cals.rest.api.domain.*;
import gov.ca.cwds.cals.rest.api.service.dto.PersonLanguageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PersonLanguage and its DTO PersonLanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, LanguageTypeMapper.class, })
public interface PersonLanguageMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "language.id", target = "languageId")
    PersonLanguageDTO personLanguageToPersonLanguageDTO(PersonLanguage personLanguage);

    List<PersonLanguageDTO> personLanguagesToPersonLanguageDTOs(List<PersonLanguage> personLanguages);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "languageId", target = "language")
    PersonLanguage personLanguageDTOToPersonLanguage(PersonLanguageDTO personLanguageDTO);

    List<PersonLanguage> personLanguageDTOsToPersonLanguages(List<PersonLanguageDTO> personLanguageDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default PersonLanguage personLanguageFromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonLanguage personLanguage = new PersonLanguage();
        personLanguage.setId(id);
        return personLanguage;
    }
    

}
