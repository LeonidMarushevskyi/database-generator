package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Person;

import gov.ca.cwds.cals.rest.api.repository.PersonRepository;
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
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";
        
    private final PersonRepository personRepository;

    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * POST  /people : Create a new person.
     *
     * @param person the person to create
     * @return the ResponseEntity with status 201 (Created) and with body the new person, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    @Timed
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
        }
        Person result = personRepository.save(person);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing person.
     *
     * @param person the person to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated person,
     * or with status 400 (Bad Request) if the person is not valid,
     * or with status 500 (Internal Server Error) if the person couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    @Timed
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            return createPerson(person);
        }
        Person result = personRepository.save(person);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, person.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     */
    @GetMapping("/people")
    @Timed
    public List<Person> getAllPeople() {
        log.debug("REST request to get all People");
        List<Person> people = personRepository.findAllWithEagerRelationships();
        return people;
    }

    /**
     * GET  /people/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the person, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(person));
    }

    /**
     * DELETE  /people/:id : delete the "id" person.
     *
     * @param id the id of the person to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    @Timed
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
