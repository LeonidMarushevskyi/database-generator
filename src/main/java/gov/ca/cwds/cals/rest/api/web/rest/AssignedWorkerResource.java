package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.AssignedWorker;

import gov.ca.cwds.cals.rest.api.repository.AssignedWorkerRepository;
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
 * REST controller for managing AssignedWorker.
 */
@RestController
@RequestMapping("/api")
public class AssignedWorkerResource {

    private final Logger log = LoggerFactory.getLogger(AssignedWorkerResource.class);

    private static final String ENTITY_NAME = "assignedWorker";
        
    private final AssignedWorkerRepository assignedWorkerRepository;

    public AssignedWorkerResource(AssignedWorkerRepository assignedWorkerRepository) {
        this.assignedWorkerRepository = assignedWorkerRepository;
    }

    /**
     * POST  /assigned-workers : Create a new assignedWorker.
     *
     * @param assignedWorker the assignedWorker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assignedWorker, or with status 400 (Bad Request) if the assignedWorker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assigned-workers")
    @Timed
    public ResponseEntity<AssignedWorker> createAssignedWorker(@Valid @RequestBody AssignedWorker assignedWorker) throws URISyntaxException {
        log.debug("REST request to save AssignedWorker : {}", assignedWorker);
        if (assignedWorker.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assignedWorker cannot already have an ID")).body(null);
        }
        AssignedWorker result = assignedWorkerRepository.save(assignedWorker);
        return ResponseEntity.created(new URI("/api/assigned-workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assigned-workers : Updates an existing assignedWorker.
     *
     * @param assignedWorker the assignedWorker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assignedWorker,
     * or with status 400 (Bad Request) if the assignedWorker is not valid,
     * or with status 500 (Internal Server Error) if the assignedWorker couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assigned-workers")
    @Timed
    public ResponseEntity<AssignedWorker> updateAssignedWorker(@Valid @RequestBody AssignedWorker assignedWorker) throws URISyntaxException {
        log.debug("REST request to update AssignedWorker : {}", assignedWorker);
        if (assignedWorker.getId() == null) {
            return createAssignedWorker(assignedWorker);
        }
        AssignedWorker result = assignedWorkerRepository.save(assignedWorker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assignedWorker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assigned-workers : get all the assignedWorkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assignedWorkers in body
     */
    @GetMapping("/assigned-workers")
    @Timed
    public List<AssignedWorker> getAllAssignedWorkers() {
        log.debug("REST request to get all AssignedWorkers");
        List<AssignedWorker> assignedWorkers = assignedWorkerRepository.findAll();
        return assignedWorkers;
    }

    /**
     * GET  /assigned-workers/:id : get the "id" assignedWorker.
     *
     * @param id the id of the assignedWorker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assignedWorker, or with status 404 (Not Found)
     */
    @GetMapping("/assigned-workers/{id}")
    @Timed
    public ResponseEntity<AssignedWorker> getAssignedWorker(@PathVariable Long id) {
        log.debug("REST request to get AssignedWorker : {}", id);
        AssignedWorker assignedWorker = assignedWorkerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assignedWorker));
    }

    /**
     * DELETE  /assigned-workers/:id : delete the "id" assignedWorker.
     *
     * @param id the id of the assignedWorker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assigned-workers/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssignedWorker(@PathVariable Long id) {
        log.debug("REST request to delete AssignedWorker : {}", id);
        assignedWorkerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
