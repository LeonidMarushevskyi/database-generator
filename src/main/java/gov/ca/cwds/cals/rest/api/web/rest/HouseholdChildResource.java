package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.HouseholdChild;

import gov.ca.cwds.cals.rest.api.repository.HouseholdChildRepository;
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
 * REST controller for managing HouseholdChild.
 */
@RestController
@RequestMapping("/api")
public class HouseholdChildResource {

    private final Logger log = LoggerFactory.getLogger(HouseholdChildResource.class);

    private static final String ENTITY_NAME = "householdChild";
        
    private final HouseholdChildRepository householdChildRepository;

    public HouseholdChildResource(HouseholdChildRepository householdChildRepository) {
        this.householdChildRepository = householdChildRepository;
    }

    /**
     * POST  /household-children : Create a new householdChild.
     *
     * @param householdChild the householdChild to create
     * @return the ResponseEntity with status 201 (Created) and with body the new householdChild, or with status 400 (Bad Request) if the householdChild has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/household-children")
    @Timed
    public ResponseEntity<HouseholdChild> createHouseholdChild(@Valid @RequestBody HouseholdChild householdChild) throws URISyntaxException {
        log.debug("REST request to save HouseholdChild : {}", householdChild);
        if (householdChild.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new householdChild cannot already have an ID")).body(null);
        }
        HouseholdChild result = householdChildRepository.save(householdChild);
        return ResponseEntity.created(new URI("/api/household-children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /household-children : Updates an existing householdChild.
     *
     * @param householdChild the householdChild to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated householdChild,
     * or with status 400 (Bad Request) if the householdChild is not valid,
     * or with status 500 (Internal Server Error) if the householdChild couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/household-children")
    @Timed
    public ResponseEntity<HouseholdChild> updateHouseholdChild(@Valid @RequestBody HouseholdChild householdChild) throws URISyntaxException {
        log.debug("REST request to update HouseholdChild : {}", householdChild);
        if (householdChild.getId() == null) {
            return createHouseholdChild(householdChild);
        }
        HouseholdChild result = householdChildRepository.save(householdChild);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, householdChild.getId().toString()))
            .body(result);
    }

    /**
     * GET  /household-children : get all the householdChildren.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of householdChildren in body
     */
    @GetMapping("/household-children")
    @Timed
    public List<HouseholdChild> getAllHouseholdChildren() {
        log.debug("REST request to get all HouseholdChildren");
        List<HouseholdChild> householdChildren = householdChildRepository.findAll();
        return householdChildren;
    }

    /**
     * GET  /household-children/:id : get the "id" householdChild.
     *
     * @param id the id of the householdChild to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the householdChild, or with status 404 (Not Found)
     */
    @GetMapping("/household-children/{id}")
    @Timed
    public ResponseEntity<HouseholdChild> getHouseholdChild(@PathVariable Long id) {
        log.debug("REST request to get HouseholdChild : {}", id);
        HouseholdChild householdChild = householdChildRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(householdChild));
    }

    /**
     * DELETE  /household-children/:id : delete the "id" householdChild.
     *
     * @param id the id of the householdChild to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/household-children/{id}")
    @Timed
    public ResponseEntity<Void> deleteHouseholdChild(@PathVariable Long id) {
        log.debug("REST request to delete HouseholdChild : {}", id);
        householdChildRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
