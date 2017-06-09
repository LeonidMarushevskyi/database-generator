package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.DeterminedChild;

import gov.ca.cwds.cals.rest.api.repository.DeterminedChildRepository;
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
 * REST controller for managing DeterminedChild.
 */
@RestController
@RequestMapping("/api")
public class DeterminedChildResource {

    private final Logger log = LoggerFactory.getLogger(DeterminedChildResource.class);

    private static final String ENTITY_NAME = "determinedChild";
        
    private final DeterminedChildRepository determinedChildRepository;

    public DeterminedChildResource(DeterminedChildRepository determinedChildRepository) {
        this.determinedChildRepository = determinedChildRepository;
    }

    /**
     * POST  /determined-children : Create a new determinedChild.
     *
     * @param determinedChild the determinedChild to create
     * @return the ResponseEntity with status 201 (Created) and with body the new determinedChild, or with status 400 (Bad Request) if the determinedChild has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/determined-children")
    @Timed
    public ResponseEntity<DeterminedChild> createDeterminedChild(@Valid @RequestBody DeterminedChild determinedChild) throws URISyntaxException {
        log.debug("REST request to save DeterminedChild : {}", determinedChild);
        if (determinedChild.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new determinedChild cannot already have an ID")).body(null);
        }
        DeterminedChild result = determinedChildRepository.save(determinedChild);
        return ResponseEntity.created(new URI("/api/determined-children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /determined-children : Updates an existing determinedChild.
     *
     * @param determinedChild the determinedChild to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated determinedChild,
     * or with status 400 (Bad Request) if the determinedChild is not valid,
     * or with status 500 (Internal Server Error) if the determinedChild couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/determined-children")
    @Timed
    public ResponseEntity<DeterminedChild> updateDeterminedChild(@Valid @RequestBody DeterminedChild determinedChild) throws URISyntaxException {
        log.debug("REST request to update DeterminedChild : {}", determinedChild);
        if (determinedChild.getId() == null) {
            return createDeterminedChild(determinedChild);
        }
        DeterminedChild result = determinedChildRepository.save(determinedChild);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, determinedChild.getId().toString()))
            .body(result);
    }

    /**
     * GET  /determined-children : get all the determinedChildren.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of determinedChildren in body
     */
    @GetMapping("/determined-children")
    @Timed
    public List<DeterminedChild> getAllDeterminedChildren() {
        log.debug("REST request to get all DeterminedChildren");
        List<DeterminedChild> determinedChildren = determinedChildRepository.findAll();
        return determinedChildren;
    }

    /**
     * GET  /determined-children/:id : get the "id" determinedChild.
     *
     * @param id the id of the determinedChild to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the determinedChild, or with status 404 (Not Found)
     */
    @GetMapping("/determined-children/{id}")
    @Timed
    public ResponseEntity<DeterminedChild> getDeterminedChild(@PathVariable Long id) {
        log.debug("REST request to get DeterminedChild : {}", id);
        DeterminedChild determinedChild = determinedChildRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(determinedChild));
    }

    /**
     * DELETE  /determined-children/:id : delete the "id" determinedChild.
     *
     * @param id the id of the determinedChild to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/determined-children/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeterminedChild(@PathVariable Long id) {
        log.debug("REST request to delete DeterminedChild : {}", id);
        determinedChildRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
