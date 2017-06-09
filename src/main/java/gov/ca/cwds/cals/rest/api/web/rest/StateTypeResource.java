package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.StateType;

import gov.ca.cwds.cals.rest.api.repository.StateTypeRepository;
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
 * REST controller for managing StateType.
 */
@RestController
@RequestMapping("/api")
public class StateTypeResource {

    private final Logger log = LoggerFactory.getLogger(StateTypeResource.class);

    private static final String ENTITY_NAME = "stateType";
        
    private final StateTypeRepository stateTypeRepository;

    public StateTypeResource(StateTypeRepository stateTypeRepository) {
        this.stateTypeRepository = stateTypeRepository;
    }

    /**
     * POST  /state-types : Create a new stateType.
     *
     * @param stateType the stateType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stateType, or with status 400 (Bad Request) if the stateType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/state-types")
    @Timed
    public ResponseEntity<StateType> createStateType(@Valid @RequestBody StateType stateType) throws URISyntaxException {
        log.debug("REST request to save StateType : {}", stateType);
        if (stateType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stateType cannot already have an ID")).body(null);
        }
        StateType result = stateTypeRepository.save(stateType);
        return ResponseEntity.created(new URI("/api/state-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /state-types : Updates an existing stateType.
     *
     * @param stateType the stateType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stateType,
     * or with status 400 (Bad Request) if the stateType is not valid,
     * or with status 500 (Internal Server Error) if the stateType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/state-types")
    @Timed
    public ResponseEntity<StateType> updateStateType(@Valid @RequestBody StateType stateType) throws URISyntaxException {
        log.debug("REST request to update StateType : {}", stateType);
        if (stateType.getId() == null) {
            return createStateType(stateType);
        }
        StateType result = stateTypeRepository.save(stateType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stateType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /state-types : get all the stateTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stateTypes in body
     */
    @GetMapping("/state-types")
    @Timed
    public List<StateType> getAllStateTypes() {
        log.debug("REST request to get all StateTypes");
        List<StateType> stateTypes = stateTypeRepository.findAll();
        return stateTypes;
    }

    /**
     * GET  /state-types/:id : get the "id" stateType.
     *
     * @param id the id of the stateType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stateType, or with status 404 (Not Found)
     */
    @GetMapping("/state-types/{id}")
    @Timed
    public ResponseEntity<StateType> getStateType(@PathVariable Long id) {
        log.debug("REST request to get StateType : {}", id);
        StateType stateType = stateTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stateType));
    }

    /**
     * DELETE  /state-types/:id : delete the "id" stateType.
     *
     * @param id the id of the stateType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/state-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteStateType(@PathVariable Long id) {
        log.debug("REST request to delete StateType : {}", id);
        stateTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
