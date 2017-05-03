package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityType;

import gov.ca.cwds.cals.rest.api.repository.FacilityTypeRepository;
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
 * REST controller for managing FacilityType.
 */
@RestController
@RequestMapping("/api")
public class FacilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(FacilityTypeResource.class);

    private static final String ENTITY_NAME = "facilityType";
        
    private final FacilityTypeRepository facilityTypeRepository;

    public FacilityTypeResource(FacilityTypeRepository facilityTypeRepository) {
        this.facilityTypeRepository = facilityTypeRepository;
    }

    /**
     * POST  /facility-types : Create a new facilityType.
     *
     * @param facilityType the facilityType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityType, or with status 400 (Bad Request) if the facilityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-types")
    @Timed
    public ResponseEntity<FacilityType> createFacilityType(@Valid @RequestBody FacilityType facilityType) throws URISyntaxException {
        log.debug("REST request to save FacilityType : {}", facilityType);
        if (facilityType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityType cannot already have an ID")).body(null);
        }
        FacilityType result = facilityTypeRepository.save(facilityType);
        return ResponseEntity.created(new URI("/api/facility-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-types : Updates an existing facilityType.
     *
     * @param facilityType the facilityType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityType,
     * or with status 400 (Bad Request) if the facilityType is not valid,
     * or with status 500 (Internal Server Error) if the facilityType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-types")
    @Timed
    public ResponseEntity<FacilityType> updateFacilityType(@Valid @RequestBody FacilityType facilityType) throws URISyntaxException {
        log.debug("REST request to update FacilityType : {}", facilityType);
        if (facilityType.getId() == null) {
            return createFacilityType(facilityType);
        }
        FacilityType result = facilityTypeRepository.save(facilityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-types : get all the facilityTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityTypes in body
     */
    @GetMapping("/facility-types")
    @Timed
    public List<FacilityType> getAllFacilityTypes() {
        log.debug("REST request to get all FacilityTypes");
        List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
        return facilityTypes;
    }

    /**
     * GET  /facility-types/:id : get the "id" facilityType.
     *
     * @param id the id of the facilityType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityType, or with status 404 (Not Found)
     */
    @GetMapping("/facility-types/{id}")
    @Timed
    public ResponseEntity<FacilityType> getFacilityType(@PathVariable Long id) {
        log.debug("REST request to get FacilityType : {}", id);
        FacilityType facilityType = facilityTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityType));
    }

    /**
     * DELETE  /facility-types/:id : delete the "id" facilityType.
     *
     * @param id the id of the facilityType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityType(@PathVariable Long id) {
        log.debug("REST request to delete FacilityType : {}", id);
        facilityTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
