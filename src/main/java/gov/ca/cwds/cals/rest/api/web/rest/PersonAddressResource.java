package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonAddress;

import gov.ca.cwds.cals.rest.api.repository.PersonAddressRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PersonAddressDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonAddressMapper;
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
 * REST controller for managing PersonAddress.
 */
@RestController
@RequestMapping("/api")
public class PersonAddressResource {

    private final Logger log = LoggerFactory.getLogger(PersonAddressResource.class);

    private static final String ENTITY_NAME = "personAddress";
        
    private final PersonAddressRepository personAddressRepository;

    private final PersonAddressMapper personAddressMapper;

    public PersonAddressResource(PersonAddressRepository personAddressRepository, PersonAddressMapper personAddressMapper) {
        this.personAddressRepository = personAddressRepository;
        this.personAddressMapper = personAddressMapper;
    }

    /**
     * POST  /person-addresses : Create a new personAddress.
     *
     * @param personAddressDTO the personAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personAddressDTO, or with status 400 (Bad Request) if the personAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-addresses")
    @Timed
    public ResponseEntity<PersonAddressDTO> createPersonAddress(@RequestBody PersonAddressDTO personAddressDTO) throws URISyntaxException {
        log.debug("REST request to save PersonAddress : {}", personAddressDTO);
        if (personAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personAddress cannot already have an ID")).body(null);
        }
        PersonAddress personAddress = personAddressMapper.toEntity(personAddressDTO);
        personAddress = personAddressRepository.save(personAddress);
        PersonAddressDTO result = personAddressMapper.toDto(personAddress);
        return ResponseEntity.created(new URI("/api/person-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-addresses : Updates an existing personAddress.
     *
     * @param personAddressDTO the personAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personAddressDTO,
     * or with status 400 (Bad Request) if the personAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the personAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-addresses")
    @Timed
    public ResponseEntity<PersonAddressDTO> updatePersonAddress(@RequestBody PersonAddressDTO personAddressDTO) throws URISyntaxException {
        log.debug("REST request to update PersonAddress : {}", personAddressDTO);
        if (personAddressDTO.getId() == null) {
            return createPersonAddress(personAddressDTO);
        }
        PersonAddress personAddress = personAddressMapper.toEntity(personAddressDTO);
        personAddress = personAddressRepository.save(personAddress);
        PersonAddressDTO result = personAddressMapper.toDto(personAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-addresses : get all the personAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personAddresses in body
     */
    @GetMapping("/person-addresses")
    @Timed
    public List<PersonAddressDTO> getAllPersonAddresses() {
        log.debug("REST request to get all PersonAddresses");
        List<PersonAddress> personAddresses = personAddressRepository.findAll();
        return personAddressMapper.toDto(personAddresses);
    }

    /**
     * GET  /person-addresses/:id : get the "id" personAddress.
     *
     * @param id the id of the personAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-addresses/{id}")
    @Timed
    public ResponseEntity<PersonAddressDTO> getPersonAddress(@PathVariable Long id) {
        log.debug("REST request to get PersonAddress : {}", id);
        PersonAddress personAddress = personAddressRepository.findOne(id);
        PersonAddressDTO personAddressDTO = personAddressMapper.toDto(personAddress);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personAddressDTO));
    }

    /**
     * DELETE  /person-addresses/:id : delete the "id" personAddress.
     *
     * @param id the id of the personAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonAddress(@PathVariable Long id) {
        log.debug("REST request to delete PersonAddress : {}", id);
        personAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
