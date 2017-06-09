package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.HouseholdAdult;

import gov.ca.cwds.cals.rest.api.repository.HouseholdAdultRepository;
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
 * REST controller for managing HouseholdAdult.
 */
@RestController
@RequestMapping("/api")
public class HouseholdAdultResource {

    private final Logger log = LoggerFactory.getLogger(HouseholdAdultResource.class);

    private static final String ENTITY_NAME = "householdAdult";
        
    private final HouseholdAdultRepository householdAdultRepository;

    public HouseholdAdultResource(HouseholdAdultRepository householdAdultRepository) {
        this.householdAdultRepository = householdAdultRepository;
    }

    /**
     * POST  /household-adults : Create a new householdAdult.
     *
     * @param householdAdult the householdAdult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new householdAdult, or with status 400 (Bad Request) if the householdAdult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/household-adults")
    @Timed
    public ResponseEntity<HouseholdAdult> createHouseholdAdult(@Valid @RequestBody HouseholdAdult householdAdult) throws URISyntaxException {
        log.debug("REST request to save HouseholdAdult : {}", householdAdult);
        if (householdAdult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new householdAdult cannot already have an ID")).body(null);
        }
        HouseholdAdult result = householdAdultRepository.save(householdAdult);
        return ResponseEntity.created(new URI("/api/household-adults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /household-adults : Updates an existing householdAdult.
     *
     * @param householdAdult the householdAdult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated householdAdult,
     * or with status 400 (Bad Request) if the householdAdult is not valid,
     * or with status 500 (Internal Server Error) if the householdAdult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/household-adults")
    @Timed
    public ResponseEntity<HouseholdAdult> updateHouseholdAdult(@Valid @RequestBody HouseholdAdult householdAdult) throws URISyntaxException {
        log.debug("REST request to update HouseholdAdult : {}", householdAdult);
        if (householdAdult.getId() == null) {
            return createHouseholdAdult(householdAdult);
        }
        HouseholdAdult result = householdAdultRepository.save(householdAdult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, householdAdult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /household-adults : get all the householdAdults.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of householdAdults in body
     */
    @GetMapping("/household-adults")
    @Timed
    public List<HouseholdAdult> getAllHouseholdAdults() {
        log.debug("REST request to get all HouseholdAdults");
        List<HouseholdAdult> householdAdults = householdAdultRepository.findAllWithEagerRelationships();
        return householdAdults;
    }

    /**
     * GET  /household-adults/:id : get the "id" householdAdult.
     *
     * @param id the id of the householdAdult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the householdAdult, or with status 404 (Not Found)
     */
    @GetMapping("/household-adults/{id}")
    @Timed
    public ResponseEntity<HouseholdAdult> getHouseholdAdult(@PathVariable Long id) {
        log.debug("REST request to get HouseholdAdult : {}", id);
        HouseholdAdult householdAdult = householdAdultRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(householdAdult));
    }

    /**
     * DELETE  /household-adults/:id : delete the "id" householdAdult.
     *
     * @param id the id of the householdAdult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/household-adults/{id}")
    @Timed
    public ResponseEntity<Void> deleteHouseholdAdult(@PathVariable Long id) {
        log.debug("REST request to delete HouseholdAdult : {}", id);
        householdAdultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
