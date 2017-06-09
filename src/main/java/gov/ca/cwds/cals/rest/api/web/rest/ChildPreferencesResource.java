package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.ChildPreferences;

import gov.ca.cwds.cals.rest.api.repository.ChildPreferencesRepository;
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
 * REST controller for managing ChildPreferences.
 */
@RestController
@RequestMapping("/api")
public class ChildPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(ChildPreferencesResource.class);

    private static final String ENTITY_NAME = "childPreferences";
        
    private final ChildPreferencesRepository childPreferencesRepository;

    public ChildPreferencesResource(ChildPreferencesRepository childPreferencesRepository) {
        this.childPreferencesRepository = childPreferencesRepository;
    }

    /**
     * POST  /child-preferences : Create a new childPreferences.
     *
     * @param childPreferences the childPreferences to create
     * @return the ResponseEntity with status 201 (Created) and with body the new childPreferences, or with status 400 (Bad Request) if the childPreferences has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/child-preferences")
    @Timed
    public ResponseEntity<ChildPreferences> createChildPreferences(@Valid @RequestBody ChildPreferences childPreferences) throws URISyntaxException {
        log.debug("REST request to save ChildPreferences : {}", childPreferences);
        if (childPreferences.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new childPreferences cannot already have an ID")).body(null);
        }
        ChildPreferences result = childPreferencesRepository.save(childPreferences);
        return ResponseEntity.created(new URI("/api/child-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /child-preferences : Updates an existing childPreferences.
     *
     * @param childPreferences the childPreferences to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated childPreferences,
     * or with status 400 (Bad Request) if the childPreferences is not valid,
     * or with status 500 (Internal Server Error) if the childPreferences couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/child-preferences")
    @Timed
    public ResponseEntity<ChildPreferences> updateChildPreferences(@Valid @RequestBody ChildPreferences childPreferences) throws URISyntaxException {
        log.debug("REST request to update ChildPreferences : {}", childPreferences);
        if (childPreferences.getId() == null) {
            return createChildPreferences(childPreferences);
        }
        ChildPreferences result = childPreferencesRepository.save(childPreferences);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, childPreferences.getId().toString()))
            .body(result);
    }

    /**
     * GET  /child-preferences : get all the childPreferences.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of childPreferences in body
     */
    @GetMapping("/child-preferences")
    @Timed
    public List<ChildPreferences> getAllChildPreferences(@RequestParam(required = false) String filter) {
        if ("application-is-null".equals(filter)) {
            log.debug("REST request to get all ChildPreferencess where application is null");
            return StreamSupport
                .stream(childPreferencesRepository.findAll().spliterator(), false)
                .filter(childPreferences -> childPreferences.getApplication() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ChildPreferences");
        List<ChildPreferences> childPreferences = childPreferencesRepository.findAllWithEagerRelationships();
        return childPreferences;
    }

    /**
     * GET  /child-preferences/:id : get the "id" childPreferences.
     *
     * @param id the id of the childPreferences to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the childPreferences, or with status 404 (Not Found)
     */
    @GetMapping("/child-preferences/{id}")
    @Timed
    public ResponseEntity<ChildPreferences> getChildPreferences(@PathVariable Long id) {
        log.debug("REST request to get ChildPreferences : {}", id);
        ChildPreferences childPreferences = childPreferencesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(childPreferences));
    }

    /**
     * DELETE  /child-preferences/:id : delete the "id" childPreferences.
     *
     * @param id the id of the childPreferences to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/child-preferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteChildPreferences(@PathVariable Long id) {
        log.debug("REST request to delete ChildPreferences : {}", id);
        childPreferencesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
