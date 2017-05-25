package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonEthnicity;

import gov.ca.cwds.cals.rest.api.repository.PersonEthnicityRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PersonEthnicityDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonEthnicityMapper;
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
 * REST controller for managing PersonEthnicity.
 */
@RestController
@RequestMapping("/api")
public class PersonEthnicityResource {

    private final Logger log = LoggerFactory.getLogger(PersonEthnicityResource.class);

    private static final String ENTITY_NAME = "personEthnicity";
        
    private final PersonEthnicityRepository personEthnicityRepository;

    private final PersonEthnicityMapper personEthnicityMapper;

    public PersonEthnicityResource(PersonEthnicityRepository personEthnicityRepository, PersonEthnicityMapper personEthnicityMapper) {
        this.personEthnicityRepository = personEthnicityRepository;
        this.personEthnicityMapper = personEthnicityMapper;
    }

    /**
     * POST  /person-ethnicities : Create a new personEthnicity.
     *
     * @param personEthnicityDTO the personEthnicityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personEthnicityDTO, or with status 400 (Bad Request) if the personEthnicity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-ethnicities")
    @Timed
    public ResponseEntity<PersonEthnicityDTO> createPersonEthnicity(@RequestBody PersonEthnicityDTO personEthnicityDTO) throws URISyntaxException {
        log.debug("REST request to save PersonEthnicity : {}", personEthnicityDTO);
        if (personEthnicityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personEthnicity cannot already have an ID")).body(null);
        }
        PersonEthnicity personEthnicity = personEthnicityMapper.toEntity(personEthnicityDTO);
        personEthnicity = personEthnicityRepository.save(personEthnicity);
        PersonEthnicityDTO result = personEthnicityMapper.toDto(personEthnicity);
        return ResponseEntity.created(new URI("/api/person-ethnicities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-ethnicities : Updates an existing personEthnicity.
     *
     * @param personEthnicityDTO the personEthnicityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personEthnicityDTO,
     * or with status 400 (Bad Request) if the personEthnicityDTO is not valid,
     * or with status 500 (Internal Server Error) if the personEthnicityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-ethnicities")
    @Timed
    public ResponseEntity<PersonEthnicityDTO> updatePersonEthnicity(@RequestBody PersonEthnicityDTO personEthnicityDTO) throws URISyntaxException {
        log.debug("REST request to update PersonEthnicity : {}", personEthnicityDTO);
        if (personEthnicityDTO.getId() == null) {
            return createPersonEthnicity(personEthnicityDTO);
        }
        PersonEthnicity personEthnicity = personEthnicityMapper.toEntity(personEthnicityDTO);
        personEthnicity = personEthnicityRepository.save(personEthnicity);
        PersonEthnicityDTO result = personEthnicityMapper.toDto(personEthnicity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personEthnicityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-ethnicities : get all the personEthnicities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personEthnicities in body
     */
    @GetMapping("/person-ethnicities")
    @Timed
    public List<PersonEthnicityDTO> getAllPersonEthnicities() {
        log.debug("REST request to get all PersonEthnicities");
        List<PersonEthnicity> personEthnicities = personEthnicityRepository.findAll();
        return personEthnicityMapper.toDto(personEthnicities);
    }

    /**
     * GET  /person-ethnicities/:id : get the "id" personEthnicity.
     *
     * @param id the id of the personEthnicityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personEthnicityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-ethnicities/{id}")
    @Timed
    public ResponseEntity<PersonEthnicityDTO> getPersonEthnicity(@PathVariable Long id) {
        log.debug("REST request to get PersonEthnicity : {}", id);
        PersonEthnicity personEthnicity = personEthnicityRepository.findOne(id);
        PersonEthnicityDTO personEthnicityDTO = personEthnicityMapper.toDto(personEthnicity);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personEthnicityDTO));
    }

    /**
     * DELETE  /person-ethnicities/:id : delete the "id" personEthnicity.
     *
     * @param id the id of the personEthnicityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-ethnicities/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonEthnicity(@PathVariable Long id) {
        log.debug("REST request to delete PersonEthnicity : {}", id);
        personEthnicityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
