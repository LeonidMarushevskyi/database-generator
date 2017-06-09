package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.RelationshipType;

import gov.ca.cwds.cals.rest.api.repository.RelationshipTypeRepository;
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
 * REST controller for managing RelationshipType.
 */
@RestController
@RequestMapping("/api")
public class RelationshipTypeResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeResource.class);

    private static final String ENTITY_NAME = "relationshipType";
        
    private final RelationshipTypeRepository relationshipTypeRepository;

    public RelationshipTypeResource(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    /**
     * POST  /relationship-types : Create a new relationshipType.
     *
     * @param relationshipType the relationshipType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relationshipType, or with status 400 (Bad Request) if the relationshipType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relationship-types")
    @Timed
    public ResponseEntity<RelationshipType> createRelationshipType(@Valid @RequestBody RelationshipType relationshipType) throws URISyntaxException {
        log.debug("REST request to save RelationshipType : {}", relationshipType);
        if (relationshipType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new relationshipType cannot already have an ID")).body(null);
        }
        RelationshipType result = relationshipTypeRepository.save(relationshipType);
        return ResponseEntity.created(new URI("/api/relationship-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationship-types : Updates an existing relationshipType.
     *
     * @param relationshipType the relationshipType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relationshipType,
     * or with status 400 (Bad Request) if the relationshipType is not valid,
     * or with status 500 (Internal Server Error) if the relationshipType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relationship-types")
    @Timed
    public ResponseEntity<RelationshipType> updateRelationshipType(@Valid @RequestBody RelationshipType relationshipType) throws URISyntaxException {
        log.debug("REST request to update RelationshipType : {}", relationshipType);
        if (relationshipType.getId() == null) {
            return createRelationshipType(relationshipType);
        }
        RelationshipType result = relationshipTypeRepository.save(relationshipType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, relationshipType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationship-types : get all the relationshipTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of relationshipTypes in body
     */
    @GetMapping("/relationship-types")
    @Timed
    public List<RelationshipType> getAllRelationshipTypes() {
        log.debug("REST request to get all RelationshipTypes");
        List<RelationshipType> relationshipTypes = relationshipTypeRepository.findAll();
        return relationshipTypes;
    }

    /**
     * GET  /relationship-types/:id : get the "id" relationshipType.
     *
     * @param id the id of the relationshipType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relationshipType, or with status 404 (Not Found)
     */
    @GetMapping("/relationship-types/{id}")
    @Timed
    public ResponseEntity<RelationshipType> getRelationshipType(@PathVariable Long id) {
        log.debug("REST request to get RelationshipType : {}", id);
        RelationshipType relationshipType = relationshipTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(relationshipType));
    }

    /**
     * DELETE  /relationship-types/:id : delete the "id" relationshipType.
     *
     * @param id the id of the relationshipType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relationship-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelationshipType(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipType : {}", id);
        relationshipTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
