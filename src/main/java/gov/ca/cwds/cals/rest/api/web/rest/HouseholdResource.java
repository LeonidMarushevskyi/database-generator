package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Household;

import gov.ca.cwds.cals.rest.api.repository.HouseholdRepository;
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
 * REST controller for managing Household.
 */
@RestController
@RequestMapping("/api")
public class HouseholdResource {

    private final Logger log = LoggerFactory.getLogger(HouseholdResource.class);

    private static final String ENTITY_NAME = "household";
        
    private final HouseholdRepository householdRepository;

    public HouseholdResource(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    /**
     * POST  /households : Create a new household.
     *
     * @param household the household to create
     * @return the ResponseEntity with status 201 (Created) and with body the new household, or with status 400 (Bad Request) if the household has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/households")
    @Timed
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody Household household) throws URISyntaxException {
        log.debug("REST request to save Household : {}", household);
        if (household.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new household cannot already have an ID")).body(null);
        }
        Household result = householdRepository.save(household);
        return ResponseEntity.created(new URI("/api/households/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /households : Updates an existing household.
     *
     * @param household the household to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated household,
     * or with status 400 (Bad Request) if the household is not valid,
     * or with status 500 (Internal Server Error) if the household couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/households")
    @Timed
    public ResponseEntity<Household> updateHousehold(@Valid @RequestBody Household household) throws URISyntaxException {
        log.debug("REST request to update Household : {}", household);
        if (household.getId() == null) {
            return createHousehold(household);
        }
        Household result = householdRepository.save(household);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, household.getId().toString()))
            .body(result);
    }

    /**
     * GET  /households : get all the households.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of households in body
     */
    @GetMapping("/households")
    @Timed
    public List<Household> getAllHouseholds() {
        log.debug("REST request to get all Households");
        List<Household> households = householdRepository.findAllWithEagerRelationships();
        return households;
    }

    /**
     * GET  /households/:id : get the "id" household.
     *
     * @param id the id of the household to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the household, or with status 404 (Not Found)
     */
    @GetMapping("/households/{id}")
    @Timed
    public ResponseEntity<Household> getHousehold(@PathVariable Long id) {
        log.debug("REST request to get Household : {}", id);
        Household household = householdRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(household));
    }

    /**
     * DELETE  /households/:id : delete the "id" household.
     *
     * @param id the id of the household to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/households/{id}")
    @Timed
    public ResponseEntity<Void> deleteHousehold(@PathVariable Long id) {
        log.debug("REST request to delete Household : {}", id);
        householdRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
