package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonPhone;

import gov.ca.cwds.cals.rest.api.repository.PersonPhoneRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PersonPhoneDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonPhoneMapper;
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
 * REST controller for managing PersonPhone.
 */
@RestController
@RequestMapping("/api")
public class PersonPhoneResource {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneResource.class);

    private static final String ENTITY_NAME = "personPhone";
        
    private final PersonPhoneRepository personPhoneRepository;

    private final PersonPhoneMapper personPhoneMapper;

    public PersonPhoneResource(PersonPhoneRepository personPhoneRepository, PersonPhoneMapper personPhoneMapper) {
        this.personPhoneRepository = personPhoneRepository;
        this.personPhoneMapper = personPhoneMapper;
    }

    /**
     * POST  /person-phones : Create a new personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPhoneDTO, or with status 400 (Bad Request) if the personPhone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-phones")
    @Timed
    public ResponseEntity<PersonPhoneDTO> createPersonPhone(@RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personPhone cannot already have an ID")).body(null);
        }
        PersonPhone personPhone = personPhoneMapper.toEntity(personPhoneDTO);
        personPhone = personPhoneRepository.save(personPhone);
        PersonPhoneDTO result = personPhoneMapper.toDto(personPhone);
        return ResponseEntity.created(new URI("/api/person-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-phones : Updates an existing personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPhoneDTO,
     * or with status 400 (Bad Request) if the personPhoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the personPhoneDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-phones")
    @Timed
    public ResponseEntity<PersonPhoneDTO> updatePersonPhone(@RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() == null) {
            return createPersonPhone(personPhoneDTO);
        }
        PersonPhone personPhone = personPhoneMapper.toEntity(personPhoneDTO);
        personPhone = personPhoneRepository.save(personPhone);
        PersonPhoneDTO result = personPhoneMapper.toDto(personPhone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-phones : get all the personPhones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personPhones in body
     */
    @GetMapping("/person-phones")
    @Timed
    public List<PersonPhoneDTO> getAllPersonPhones() {
        log.debug("REST request to get all PersonPhones");
        List<PersonPhone> personPhones = personPhoneRepository.findAll();
        return personPhoneMapper.toDto(personPhones);
    }

    /**
     * GET  /person-phones/:id : get the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPhoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-phones/{id}")
    @Timed
    public ResponseEntity<PersonPhoneDTO> getPersonPhone(@PathVariable Long id) {
        log.debug("REST request to get PersonPhone : {}", id);
        PersonPhone personPhone = personPhoneRepository.findOne(id);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personPhoneDTO));
    }

    /**
     * DELETE  /person-phones/:id : delete the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-phones/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonPhone(@PathVariable Long id) {
        log.debug("REST request to delete PersonPhone : {}", id);
        personPhoneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
