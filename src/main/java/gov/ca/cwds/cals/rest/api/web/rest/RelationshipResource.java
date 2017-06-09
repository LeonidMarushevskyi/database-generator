package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Relationship;

import gov.ca.cwds.cals.rest.api.repository.RelationshipRepository;
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
 * REST controller for managing Relationship.
 */
@RestController
@RequestMapping("/api")
public class RelationshipResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipResource.class);

    private static final String ENTITY_NAME = "relationship";
        
    private final RelationshipRepository relationshipRepository;

    public RelationshipResource(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    /**
     * POST  /relationships : Create a new relationship.
     *
     * @param relationship the relationship to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relationship, or with status 400 (Bad Request) if the relationship has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relationships")
    @Timed
    public ResponseEntity<Relationship> createRelationship(@Valid @RequestBody Relationship relationship) throws URISyntaxException {
        log.debug("REST request to save Relationship : {}", relationship);
        if (relationship.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new relationship cannot already have an ID")).body(null);
        }
        Relationship result = relationshipRepository.save(relationship);
        return ResponseEntity.created(new URI("/api/relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationships : Updates an existing relationship.
     *
     * @param relationship the relationship to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relationship,
     * or with status 400 (Bad Request) if the relationship is not valid,
     * or with status 500 (Internal Server Error) if the relationship couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relationships")
    @Timed
    public ResponseEntity<Relationship> updateRelationship(@Valid @RequestBody Relationship relationship) throws URISyntaxException {
        log.debug("REST request to update Relationship : {}", relationship);
        if (relationship.getId() == null) {
            return createRelationship(relationship);
        }
        Relationship result = relationshipRepository.save(relationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, relationship.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationships : get all the relationships.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of relationships in body
     */
    @GetMapping("/relationships")
    @Timed
    public List<Relationship> getAllRelationships() {
        log.debug("REST request to get all Relationships");
        List<Relationship> relationships = relationshipRepository.findAll();
        return relationships;
    }

    /**
     * GET  /relationships/:id : get the "id" relationship.
     *
     * @param id the id of the relationship to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relationship, or with status 404 (Not Found)
     */
    @GetMapping("/relationships/{id}")
    @Timed
    public ResponseEntity<Relationship> getRelationship(@PathVariable Long id) {
        log.debug("REST request to get Relationship : {}", id);
        Relationship relationship = relationshipRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(relationship));
    }

    /**
     * DELETE  /relationships/:id : delete the "id" relationship.
     *
     * @param id the id of the relationship to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relationships/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        log.debug("REST request to delete Relationship : {}", id);
        relationshipRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
