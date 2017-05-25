package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.AssignedWorker;

import gov.ca.cwds.cals.rest.api.repository.AssignedWorkerRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.AssignedWorkerDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.AssignedWorkerMapper;
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

    private final AssignedWorkerMapper assignedWorkerMapper;

    public AssignedWorkerResource(AssignedWorkerRepository assignedWorkerRepository, AssignedWorkerMapper assignedWorkerMapper) {
        this.assignedWorkerRepository = assignedWorkerRepository;
        this.assignedWorkerMapper = assignedWorkerMapper;
    }

    /**
     * POST  /assigned-workers : Create a new assignedWorker.
     *
     * @param assignedWorkerDTO the assignedWorkerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assignedWorkerDTO, or with status 400 (Bad Request) if the assignedWorker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assigned-workers")
    @Timed
    public ResponseEntity<AssignedWorkerDTO> createAssignedWorker(@Valid @RequestBody AssignedWorkerDTO assignedWorkerDTO) throws URISyntaxException {
        log.debug("REST request to save AssignedWorker : {}", assignedWorkerDTO);
        if (assignedWorkerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assignedWorker cannot already have an ID")).body(null);
        }
        AssignedWorker assignedWorker = assignedWorkerMapper.toEntity(assignedWorkerDTO);
        assignedWorker = assignedWorkerRepository.save(assignedWorker);
        AssignedWorkerDTO result = assignedWorkerMapper.toDto(assignedWorker);
        return ResponseEntity.created(new URI("/api/assigned-workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assigned-workers : Updates an existing assignedWorker.
     *
     * @param assignedWorkerDTO the assignedWorkerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assignedWorkerDTO,
     * or with status 400 (Bad Request) if the assignedWorkerDTO is not valid,
     * or with status 500 (Internal Server Error) if the assignedWorkerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assigned-workers")
    @Timed
    public ResponseEntity<AssignedWorkerDTO> updateAssignedWorker(@Valid @RequestBody AssignedWorkerDTO assignedWorkerDTO) throws URISyntaxException {
        log.debug("REST request to update AssignedWorker : {}", assignedWorkerDTO);
        if (assignedWorkerDTO.getId() == null) {
            return createAssignedWorker(assignedWorkerDTO);
        }
        AssignedWorker assignedWorker = assignedWorkerMapper.toEntity(assignedWorkerDTO);
        assignedWorker = assignedWorkerRepository.save(assignedWorker);
        AssignedWorkerDTO result = assignedWorkerMapper.toDto(assignedWorker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assignedWorkerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assigned-workers : get all the assignedWorkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assignedWorkers in body
     */
    @GetMapping("/assigned-workers")
    @Timed
    public List<AssignedWorkerDTO> getAllAssignedWorkers() {
        log.debug("REST request to get all AssignedWorkers");
        List<AssignedWorker> assignedWorkers = assignedWorkerRepository.findAll();
        return assignedWorkerMapper.toDto(assignedWorkers);
    }

    /**
     * GET  /assigned-workers/:id : get the "id" assignedWorker.
     *
     * @param id the id of the assignedWorkerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assignedWorkerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assigned-workers/{id}")
    @Timed
    public ResponseEntity<AssignedWorkerDTO> getAssignedWorker(@PathVariable Long id) {
        log.debug("REST request to get AssignedWorker : {}", id);
        AssignedWorker assignedWorker = assignedWorkerRepository.findOne(id);
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.toDto(assignedWorker);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assignedWorkerDTO));
    }

    /**
     * DELETE  /assigned-workers/:id : delete the "id" assignedWorker.
     *
     * @param id the id of the assignedWorkerDTO to delete
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
