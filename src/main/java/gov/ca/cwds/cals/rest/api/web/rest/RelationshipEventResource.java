package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.RelationshipEvent;

import gov.ca.cwds.cals.rest.api.repository.RelationshipEventRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing RelationshipEvent.
 */
@RestController
@RequestMapping("/api")
public class RelationshipEventResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipEventResource.class);

    private static final String ENTITY_NAME = "relationshipEvent";
        
    private final RelationshipEventRepository relationshipEventRepository;

    public RelationshipEventResource(RelationshipEventRepository relationshipEventRepository) {
        this.relationshipEventRepository = relationshipEventRepository;
    }

    /**
     * POST  /relationship-events : Create a new relationshipEvent.
     *
     * @param relationshipEvent the relationshipEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relationshipEvent, or with status 400 (Bad Request) if the relationshipEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relationship-events")
    @Timed
    public ResponseEntity<RelationshipEvent> createRelationshipEvent(@Valid @RequestBody RelationshipEvent relationshipEvent) throws URISyntaxException {
        log.debug("REST request to save RelationshipEvent : {}", relationshipEvent);
        if (relationshipEvent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new relationshipEvent cannot already have an ID")).body(null);
        }
        RelationshipEvent result = relationshipEventRepository.save(relationshipEvent);
        return ResponseEntity.created(new URI("/api/relationship-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationship-events : Updates an existing relationshipEvent.
     *
     * @param relationshipEvent the relationshipEvent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relationshipEvent,
     * or with status 400 (Bad Request) if the relationshipEvent is not valid,
     * or with status 500 (Internal Server Error) if the relationshipEvent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relationship-events")
    @Timed
    public ResponseEntity<RelationshipEvent> updateRelationshipEvent(@Valid @RequestBody RelationshipEvent relationshipEvent) throws URISyntaxException {
        log.debug("REST request to update RelationshipEvent : {}", relationshipEvent);
        if (relationshipEvent.getId() == null) {
            return createRelationshipEvent(relationshipEvent);
        }
        RelationshipEvent result = relationshipEventRepository.save(relationshipEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, relationshipEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationship-events : get all the relationshipEvents.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of relationshipEvents in body
     */
    @GetMapping("/relationship-events")
    @Timed
    public List<RelationshipEvent> getAllRelationshipEvents(@RequestParam(required = false) String filter) {
        if ("relationship-is-null".equals(filter)) {
            log.debug("REST request to get all RelationshipEvents where relationShip is null");
            return StreamSupport
                .stream(relationshipEventRepository.findAll().spliterator(), false)
                .filter(relationshipEvent -> relationshipEvent.getRelationShip() == null)
                .collect(Collectors.toList());
        }
        if ("relationship-is-null".equals(filter)) {
            log.debug("REST request to get all RelationshipEvents where relationShip is null");
            return StreamSupport
                .stream(relationshipEventRepository.findAll().spliterator(), false)
                .filter(relationshipEvent -> relationshipEvent.getRelationShip() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all RelationshipEvents");
        List<RelationshipEvent> relationshipEvents = relationshipEventRepository.findAll();
        return relationshipEvents;
    }

    /**
     * GET  /relationship-events/:id : get the "id" relationshipEvent.
     *
     * @param id the id of the relationshipEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relationshipEvent, or with status 404 (Not Found)
     */
    @GetMapping("/relationship-events/{id}")
    @Timed
    public ResponseEntity<RelationshipEvent> getRelationshipEvent(@PathVariable Long id) {
        log.debug("REST request to get RelationshipEvent : {}", id);
        RelationshipEvent relationshipEvent = relationshipEventRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(relationshipEvent));
    }

    /**
     * DELETE  /relationship-events/:id : delete the "id" relationshipEvent.
     *
     * @param id the id of the relationshipEvent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relationship-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelationshipEvent(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipEvent : {}", id);
        relationshipEventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
