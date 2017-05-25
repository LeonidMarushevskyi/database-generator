package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonLanguage;

import gov.ca.cwds.cals.rest.api.repository.PersonLanguageRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PersonLanguageDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonLanguageMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonLanguage.
 */
@RestController
@RequestMapping("/api")
public class PersonLanguageResource {

    private final Logger log = LoggerFactory.getLogger(PersonLanguageResource.class);

    private static final String ENTITY_NAME = "personLanguage";
        
    private final PersonLanguageRepository personLanguageRepository;

    private final PersonLanguageMapper personLanguageMapper;

    public PersonLanguageResource(PersonLanguageRepository personLanguageRepository, PersonLanguageMapper personLanguageMapper) {
        this.personLanguageRepository = personLanguageRepository;
        this.personLanguageMapper = personLanguageMapper;
    }

    /**
     * POST  /person-languages : Create a new personLanguage.
     *
     * @param personLanguageDTO the personLanguageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personLanguageDTO, or with status 400 (Bad Request) if the personLanguage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-languages")
    @Timed
    public ResponseEntity<PersonLanguageDTO> createPersonLanguage(@RequestBody PersonLanguageDTO personLanguageDTO) throws URISyntaxException {
        log.debug("REST request to save PersonLanguage : {}", personLanguageDTO);
        if (personLanguageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personLanguage cannot already have an ID")).body(null);
        }
        PersonLanguage personLanguage = personLanguageMapper.toEntity(personLanguageDTO);
        personLanguage = personLanguageRepository.save(personLanguage);
        PersonLanguageDTO result = personLanguageMapper.toDto(personLanguage);
        return ResponseEntity.created(new URI("/api/person-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-languages : Updates an existing personLanguage.
     *
     * @param personLanguageDTO the personLanguageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personLanguageDTO,
     * or with status 400 (Bad Request) if the personLanguageDTO is not valid,
     * or with status 500 (Internal Server Error) if the personLanguageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-languages")
    @Timed
    public ResponseEntity<PersonLanguageDTO> updatePersonLanguage(@RequestBody PersonLanguageDTO personLanguageDTO) throws URISyntaxException {
        log.debug("REST request to update PersonLanguage : {}", personLanguageDTO);
        if (personLanguageDTO.getId() == null) {
            return createPersonLanguage(personLanguageDTO);
        }
        PersonLanguage personLanguage = personLanguageMapper.toEntity(personLanguageDTO);
        personLanguage = personLanguageRepository.save(personLanguage);
        PersonLanguageDTO result = personLanguageMapper.toDto(personLanguage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personLanguageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-languages : get all the personLanguages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personLanguages in body
     */
    @GetMapping("/person-languages")
    @Timed
    public List<PersonLanguageDTO> getAllPersonLanguages() {
        log.debug("REST request to get all PersonLanguages");
        List<PersonLanguage> personLanguages = personLanguageRepository.findAll();
        return personLanguageMapper.toDto(personLanguages);
    }

    /**
     * GET  /person-languages/:id : get the "id" personLanguage.
     *
     * @param id the id of the personLanguageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personLanguageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-languages/{id}")
    @Timed
    public ResponseEntity<PersonLanguageDTO> getPersonLanguage(@PathVariable Long id) {
        log.debug("REST request to get PersonLanguage : {}", id);
        PersonLanguage personLanguage = personLanguageRepository.findOne(id);
        PersonLanguageDTO personLanguageDTO = personLanguageMapper.toDto(personLanguage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personLanguageDTO));
    }

    /**
     * DELETE  /person-languages/:id : delete the "id" personLanguage.
     *
     * @param id the id of the personLanguageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-languages/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonLanguage(@PathVariable Long id) {
        log.debug("REST request to delete PersonLanguage : {}", id);
        personLanguageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
