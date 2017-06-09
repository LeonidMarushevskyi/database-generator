package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.SiblingGroupType;

import gov.ca.cwds.cals.rest.api.repository.SiblingGroupTypeRepository;
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
 * REST controller for managing SiblingGroupType.
 */
@RestController
@RequestMapping("/api")
public class SiblingGroupTypeResource {

    private final Logger log = LoggerFactory.getLogger(SiblingGroupTypeResource.class);

    private static final String ENTITY_NAME = "siblingGroupType";
        
    private final SiblingGroupTypeRepository siblingGroupTypeRepository;

    public SiblingGroupTypeResource(SiblingGroupTypeRepository siblingGroupTypeRepository) {
        this.siblingGroupTypeRepository = siblingGroupTypeRepository;
    }

    /**
     * POST  /sibling-group-types : Create a new siblingGroupType.
     *
     * @param siblingGroupType the siblingGroupType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siblingGroupType, or with status 400 (Bad Request) if the siblingGroupType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sibling-group-types")
    @Timed
    public ResponseEntity<SiblingGroupType> createSiblingGroupType(@Valid @RequestBody SiblingGroupType siblingGroupType) throws URISyntaxException {
        log.debug("REST request to save SiblingGroupType : {}", siblingGroupType);
        if (siblingGroupType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new siblingGroupType cannot already have an ID")).body(null);
        }
        SiblingGroupType result = siblingGroupTypeRepository.save(siblingGroupType);
        return ResponseEntity.created(new URI("/api/sibling-group-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sibling-group-types : Updates an existing siblingGroupType.
     *
     * @param siblingGroupType the siblingGroupType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siblingGroupType,
     * or with status 400 (Bad Request) if the siblingGroupType is not valid,
     * or with status 500 (Internal Server Error) if the siblingGroupType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sibling-group-types")
    @Timed
    public ResponseEntity<SiblingGroupType> updateSiblingGroupType(@Valid @RequestBody SiblingGroupType siblingGroupType) throws URISyntaxException {
        log.debug("REST request to update SiblingGroupType : {}", siblingGroupType);
        if (siblingGroupType.getId() == null) {
            return createSiblingGroupType(siblingGroupType);
        }
        SiblingGroupType result = siblingGroupTypeRepository.save(siblingGroupType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siblingGroupType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sibling-group-types : get all the siblingGroupTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of siblingGroupTypes in body
     */
    @GetMapping("/sibling-group-types")
    @Timed
    public List<SiblingGroupType> getAllSiblingGroupTypes() {
        log.debug("REST request to get all SiblingGroupTypes");
        List<SiblingGroupType> siblingGroupTypes = siblingGroupTypeRepository.findAll();
        return siblingGroupTypes;
    }

    /**
     * GET  /sibling-group-types/:id : get the "id" siblingGroupType.
     *
     * @param id the id of the siblingGroupType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siblingGroupType, or with status 404 (Not Found)
     */
    @GetMapping("/sibling-group-types/{id}")
    @Timed
    public ResponseEntity<SiblingGroupType> getSiblingGroupType(@PathVariable Long id) {
        log.debug("REST request to get SiblingGroupType : {}", id);
        SiblingGroupType siblingGroupType = siblingGroupTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(siblingGroupType));
    }

    /**
     * DELETE  /sibling-group-types/:id : delete the "id" siblingGroupType.
     *
     * @param id the id of the siblingGroupType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sibling-group-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSiblingGroupType(@PathVariable Long id) {
        log.debug("REST request to delete SiblingGroupType : {}", id);
        siblingGroupTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
