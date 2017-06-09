package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PosessionType;

import gov.ca.cwds.cals.rest.api.repository.PosessionTypeRepository;
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
 * REST controller for managing PosessionType.
 */
@RestController
@RequestMapping("/api")
public class PosessionTypeResource {

    private final Logger log = LoggerFactory.getLogger(PosessionTypeResource.class);

    private static final String ENTITY_NAME = "posessionType";
        
    private final PosessionTypeRepository posessionTypeRepository;

    public PosessionTypeResource(PosessionTypeRepository posessionTypeRepository) {
        this.posessionTypeRepository = posessionTypeRepository;
    }

    /**
     * POST  /posession-types : Create a new posessionType.
     *
     * @param posessionType the posessionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posessionType, or with status 400 (Bad Request) if the posessionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posession-types")
    @Timed
    public ResponseEntity<PosessionType> createPosessionType(@Valid @RequestBody PosessionType posessionType) throws URISyntaxException {
        log.debug("REST request to save PosessionType : {}", posessionType);
        if (posessionType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new posessionType cannot already have an ID")).body(null);
        }
        PosessionType result = posessionTypeRepository.save(posessionType);
        return ResponseEntity.created(new URI("/api/posession-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posession-types : Updates an existing posessionType.
     *
     * @param posessionType the posessionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated posessionType,
     * or with status 400 (Bad Request) if the posessionType is not valid,
     * or with status 500 (Internal Server Error) if the posessionType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posession-types")
    @Timed
    public ResponseEntity<PosessionType> updatePosessionType(@Valid @RequestBody PosessionType posessionType) throws URISyntaxException {
        log.debug("REST request to update PosessionType : {}", posessionType);
        if (posessionType.getId() == null) {
            return createPosessionType(posessionType);
        }
        PosessionType result = posessionTypeRepository.save(posessionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, posessionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posession-types : get all the posessionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posessionTypes in body
     */
    @GetMapping("/posession-types")
    @Timed
    public List<PosessionType> getAllPosessionTypes() {
        log.debug("REST request to get all PosessionTypes");
        List<PosessionType> posessionTypes = posessionTypeRepository.findAll();
        return posessionTypes;
    }

    /**
     * GET  /posession-types/:id : get the "id" posessionType.
     *
     * @param id the id of the posessionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the posessionType, or with status 404 (Not Found)
     */
    @GetMapping("/posession-types/{id}")
    @Timed
    public ResponseEntity<PosessionType> getPosessionType(@PathVariable Long id) {
        log.debug("REST request to get PosessionType : {}", id);
        PosessionType posessionType = posessionTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(posessionType));
    }

    /**
     * DELETE  /posession-types/:id : delete the "id" posessionType.
     *
     * @param id the id of the posessionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posession-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePosessionType(@PathVariable Long id) {
        log.debug("REST request to delete PosessionType : {}", id);
        posessionTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
