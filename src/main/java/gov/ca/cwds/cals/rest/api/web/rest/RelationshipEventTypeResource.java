package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.RelationshipEventType;

import gov.ca.cwds.cals.rest.api.repository.RelationshipEventTypeRepository;
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
 * REST controller for managing RelationshipEventType.
 */
@RestController
@RequestMapping("/api")
public class RelationshipEventTypeResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipEventTypeResource.class);

    private static final String ENTITY_NAME = "relationshipEventType";
        
    private final RelationshipEventTypeRepository relationshipEventTypeRepository;

    public RelationshipEventTypeResource(RelationshipEventTypeRepository relationshipEventTypeRepository) {
        this.relationshipEventTypeRepository = relationshipEventTypeRepository;
    }

    /**
     * POST  /relationship-event-types : Create a new relationshipEventType.
     *
     * @param relationshipEventType the relationshipEventType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relationshipEventType, or with status 400 (Bad Request) if the relationshipEventType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relationship-event-types")
    @Timed
    public ResponseEntity<RelationshipEventType> createRelationshipEventType(@Valid @RequestBody RelationshipEventType relationshipEventType) throws URISyntaxException {
        log.debug("REST request to save RelationshipEventType : {}", relationshipEventType);
        if (relationshipEventType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new relationshipEventType cannot already have an ID")).body(null);
        }
        RelationshipEventType result = relationshipEventTypeRepository.save(relationshipEventType);
        return ResponseEntity.created(new URI("/api/relationship-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationship-event-types : Updates an existing relationshipEventType.
     *
     * @param relationshipEventType the relationshipEventType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relationshipEventType,
     * or with status 400 (Bad Request) if the relationshipEventType is not valid,
     * or with status 500 (Internal Server Error) if the relationshipEventType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relationship-event-types")
    @Timed
    public ResponseEntity<RelationshipEventType> updateRelationshipEventType(@Valid @RequestBody RelationshipEventType relationshipEventType) throws URISyntaxException {
        log.debug("REST request to update RelationshipEventType : {}", relationshipEventType);
        if (relationshipEventType.getId() == null) {
            return createRelationshipEventType(relationshipEventType);
        }
        RelationshipEventType result = relationshipEventTypeRepository.save(relationshipEventType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, relationshipEventType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationship-event-types : get all the relationshipEventTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of relationshipEventTypes in body
     */
    @GetMapping("/relationship-event-types")
    @Timed
    public List<RelationshipEventType> getAllRelationshipEventTypes() {
        log.debug("REST request to get all RelationshipEventTypes");
        List<RelationshipEventType> relationshipEventTypes = relationshipEventTypeRepository.findAll();
        return relationshipEventTypes;
    }

    /**
     * GET  /relationship-event-types/:id : get the "id" relationshipEventType.
     *
     * @param id the id of the relationshipEventType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relationshipEventType, or with status 404 (Not Found)
     */
    @GetMapping("/relationship-event-types/{id}")
    @Timed
    public ResponseEntity<RelationshipEventType> getRelationshipEventType(@PathVariable Long id) {
        log.debug("REST request to get RelationshipEventType : {}", id);
        RelationshipEventType relationshipEventType = relationshipEventTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(relationshipEventType));
    }

    /**
     * DELETE  /relationship-event-types/:id : delete the "id" relationshipEventType.
     *
     * @param id the id of the relationshipEventType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relationship-event-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelationshipEventType(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipEventType : {}", id);
        relationshipEventTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
