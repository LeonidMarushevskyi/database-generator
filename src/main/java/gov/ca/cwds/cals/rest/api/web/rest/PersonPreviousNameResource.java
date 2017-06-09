package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonPreviousName;

import gov.ca.cwds.cals.rest.api.repository.PersonPreviousNameRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonPreviousName.
 */
@RestController
@RequestMapping("/api")
public class PersonPreviousNameResource {

    private final Logger log = LoggerFactory.getLogger(PersonPreviousNameResource.class);

    private static final String ENTITY_NAME = "personPreviousName";
        
    private final PersonPreviousNameRepository personPreviousNameRepository;

    public PersonPreviousNameResource(PersonPreviousNameRepository personPreviousNameRepository) {
        this.personPreviousNameRepository = personPreviousNameRepository;
    }

    /**
     * POST  /person-previous-names : Create a new personPreviousName.
     *
     * @param personPreviousName the personPreviousName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPreviousName, or with status 400 (Bad Request) if the personPreviousName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-previous-names")
    @Timed
    public ResponseEntity<PersonPreviousName> createPersonPreviousName(@Valid @RequestBody PersonPreviousName personPreviousName) throws URISyntaxException {
        log.debug("REST request to save PersonPreviousName : {}", personPreviousName);
        if (personPreviousName.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personPreviousName cannot already have an ID")).body(null);
        }
        PersonPreviousName result = personPreviousNameRepository.save(personPreviousName);
        return ResponseEntity.created(new URI("/api/person-previous-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-previous-names : Updates an existing personPreviousName.
     *
     * @param personPreviousName the personPreviousName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPreviousName,
     * or with status 400 (Bad Request) if the personPreviousName is not valid,
     * or with status 500 (Internal Server Error) if the personPreviousName couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-previous-names")
    @Timed
    public ResponseEntity<PersonPreviousName> updatePersonPreviousName(@Valid @RequestBody PersonPreviousName personPreviousName) throws URISyntaxException {
        log.debug("REST request to update PersonPreviousName : {}", personPreviousName);
        if (personPreviousName.getId() == null) {
            return createPersonPreviousName(personPreviousName);
        }
        PersonPreviousName result = personPreviousNameRepository.save(personPreviousName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personPreviousName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-previous-names : get all the personPreviousNames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personPreviousNames in body
     */
    @GetMapping("/person-previous-names")
    @Timed
    public List<PersonPreviousName> getAllPersonPreviousNames() {
        log.debug("REST request to get all PersonPreviousNames");
        List<PersonPreviousName> personPreviousNames = personPreviousNameRepository.findAll();
        return personPreviousNames;
    }

    /**
     * GET  /person-previous-names/:id : get the "id" personPreviousName.
     *
     * @param id the id of the personPreviousName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPreviousName, or with status 404 (Not Found)
     */
    @GetMapping("/person-previous-names/{id}")
    @Timed
    public ResponseEntity<PersonPreviousName> getPersonPreviousName(@PathVariable Long id) {
        log.debug("REST request to get PersonPreviousName : {}", id);
        PersonPreviousName personPreviousName = personPreviousNameRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personPreviousName));
    }

    /**
     * DELETE  /person-previous-names/:id : delete the "id" personPreviousName.
     *
     * @param id the id of the personPreviousName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-previous-names/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonPreviousName(@PathVariable Long id) {
        log.debug("REST request to delete PersonPreviousName : {}", id);
        personPreviousNameRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
