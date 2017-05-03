package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityStatus;

import gov.ca.cwds.cals.rest.api.repository.FacilityStatusRepository;
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
 * REST controller for managing FacilityStatus.
 */
@RestController
@RequestMapping("/api")
public class FacilityStatusResource {

    private final Logger log = LoggerFactory.getLogger(FacilityStatusResource.class);

    private static final String ENTITY_NAME = "facilityStatus";
        
    private final FacilityStatusRepository facilityStatusRepository;

    public FacilityStatusResource(FacilityStatusRepository facilityStatusRepository) {
        this.facilityStatusRepository = facilityStatusRepository;
    }

    /**
     * POST  /facility-statuses : Create a new facilityStatus.
     *
     * @param facilityStatus the facilityStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityStatus, or with status 400 (Bad Request) if the facilityStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-statuses")
    @Timed
    public ResponseEntity<FacilityStatus> createFacilityStatus(@Valid @RequestBody FacilityStatus facilityStatus) throws URISyntaxException {
        log.debug("REST request to save FacilityStatus : {}", facilityStatus);
        if (facilityStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityStatus cannot already have an ID")).body(null);
        }
        FacilityStatus result = facilityStatusRepository.save(facilityStatus);
        return ResponseEntity.created(new URI("/api/facility-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-statuses : Updates an existing facilityStatus.
     *
     * @param facilityStatus the facilityStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityStatus,
     * or with status 400 (Bad Request) if the facilityStatus is not valid,
     * or with status 500 (Internal Server Error) if the facilityStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-statuses")
    @Timed
    public ResponseEntity<FacilityStatus> updateFacilityStatus(@Valid @RequestBody FacilityStatus facilityStatus) throws URISyntaxException {
        log.debug("REST request to update FacilityStatus : {}", facilityStatus);
        if (facilityStatus.getId() == null) {
            return createFacilityStatus(facilityStatus);
        }
        FacilityStatus result = facilityStatusRepository.save(facilityStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-statuses : get all the facilityStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityStatuses in body
     */
    @GetMapping("/facility-statuses")
    @Timed
    public List<FacilityStatus> getAllFacilityStatuses() {
        log.debug("REST request to get all FacilityStatuses");
        List<FacilityStatus> facilityStatuses = facilityStatusRepository.findAll();
        return facilityStatuses;
    }

    /**
     * GET  /facility-statuses/:id : get the "id" facilityStatus.
     *
     * @param id the id of the facilityStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityStatus, or with status 404 (Not Found)
     */
    @GetMapping("/facility-statuses/{id}")
    @Timed
    public ResponseEntity<FacilityStatus> getFacilityStatus(@PathVariable Long id) {
        log.debug("REST request to get FacilityStatus : {}", id);
        FacilityStatus facilityStatus = facilityStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityStatus));
    }

    /**
     * DELETE  /facility-statuses/:id : delete the "id" facilityStatus.
     *
     * @param id the id of the facilityStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityStatus(@PathVariable Long id) {
        log.debug("REST request to delete FacilityStatus : {}", id);
        facilityStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
