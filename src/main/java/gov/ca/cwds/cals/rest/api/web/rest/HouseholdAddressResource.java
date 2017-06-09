package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.HouseholdAddress;

import gov.ca.cwds.cals.rest.api.repository.HouseholdAddressRepository;
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
 * REST controller for managing HouseholdAddress.
 */
@RestController
@RequestMapping("/api")
public class HouseholdAddressResource {

    private final Logger log = LoggerFactory.getLogger(HouseholdAddressResource.class);

    private static final String ENTITY_NAME = "householdAddress";
        
    private final HouseholdAddressRepository householdAddressRepository;

    public HouseholdAddressResource(HouseholdAddressRepository householdAddressRepository) {
        this.householdAddressRepository = householdAddressRepository;
    }

    /**
     * POST  /household-addresses : Create a new householdAddress.
     *
     * @param householdAddress the householdAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new householdAddress, or with status 400 (Bad Request) if the householdAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/household-addresses")
    @Timed
    public ResponseEntity<HouseholdAddress> createHouseholdAddress(@Valid @RequestBody HouseholdAddress householdAddress) throws URISyntaxException {
        log.debug("REST request to save HouseholdAddress : {}", householdAddress);
        if (householdAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new householdAddress cannot already have an ID")).body(null);
        }
        HouseholdAddress result = householdAddressRepository.save(householdAddress);
        return ResponseEntity.created(new URI("/api/household-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /household-addresses : Updates an existing householdAddress.
     *
     * @param householdAddress the householdAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated householdAddress,
     * or with status 400 (Bad Request) if the householdAddress is not valid,
     * or with status 500 (Internal Server Error) if the householdAddress couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/household-addresses")
    @Timed
    public ResponseEntity<HouseholdAddress> updateHouseholdAddress(@Valid @RequestBody HouseholdAddress householdAddress) throws URISyntaxException {
        log.debug("REST request to update HouseholdAddress : {}", householdAddress);
        if (householdAddress.getId() == null) {
            return createHouseholdAddress(householdAddress);
        }
        HouseholdAddress result = householdAddressRepository.save(householdAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, householdAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /household-addresses : get all the householdAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of householdAddresses in body
     */
    @GetMapping("/household-addresses")
    @Timed
    public List<HouseholdAddress> getAllHouseholdAddresses() {
        log.debug("REST request to get all HouseholdAddresses");
        List<HouseholdAddress> householdAddresses = householdAddressRepository.findAll();
        return householdAddresses;
    }

    /**
     * GET  /household-addresses/:id : get the "id" householdAddress.
     *
     * @param id the id of the householdAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the householdAddress, or with status 404 (Not Found)
     */
    @GetMapping("/household-addresses/{id}")
    @Timed
    public ResponseEntity<HouseholdAddress> getHouseholdAddress(@PathVariable Long id) {
        log.debug("REST request to get HouseholdAddress : {}", id);
        HouseholdAddress householdAddress = householdAddressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(householdAddress));
    }

    /**
     * DELETE  /household-addresses/:id : delete the "id" householdAddress.
     *
     * @param id the id of the householdAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/household-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteHouseholdAddress(@PathVariable Long id) {
        log.debug("REST request to delete HouseholdAddress : {}", id);
        householdAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
