package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.EthnicityType;

import gov.ca.cwds.cals.rest.api.repository.EthnicityTypeRepository;
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
 * REST controller for managing EthnicityType.
 */
@RestController
@RequestMapping("/api")
public class EthnicityTypeResource {

    private final Logger log = LoggerFactory.getLogger(EthnicityTypeResource.class);

    private static final String ENTITY_NAME = "ethnicityType";
        
    private final EthnicityTypeRepository ethnicityTypeRepository;

    public EthnicityTypeResource(EthnicityTypeRepository ethnicityTypeRepository) {
        this.ethnicityTypeRepository = ethnicityTypeRepository;
    }

    /**
     * POST  /ethnicity-types : Create a new ethnicityType.
     *
     * @param ethnicityType the ethnicityType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ethnicityType, or with status 400 (Bad Request) if the ethnicityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ethnicity-types")
    @Timed
    public ResponseEntity<EthnicityType> createEthnicityType(@Valid @RequestBody EthnicityType ethnicityType) throws URISyntaxException {
        log.debug("REST request to save EthnicityType : {}", ethnicityType);
        if (ethnicityType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ethnicityType cannot already have an ID")).body(null);
        }
        EthnicityType result = ethnicityTypeRepository.save(ethnicityType);
        return ResponseEntity.created(new URI("/api/ethnicity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ethnicity-types : Updates an existing ethnicityType.
     *
     * @param ethnicityType the ethnicityType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ethnicityType,
     * or with status 400 (Bad Request) if the ethnicityType is not valid,
     * or with status 500 (Internal Server Error) if the ethnicityType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ethnicity-types")
    @Timed
    public ResponseEntity<EthnicityType> updateEthnicityType(@Valid @RequestBody EthnicityType ethnicityType) throws URISyntaxException {
        log.debug("REST request to update EthnicityType : {}", ethnicityType);
        if (ethnicityType.getId() == null) {
            return createEthnicityType(ethnicityType);
        }
        EthnicityType result = ethnicityTypeRepository.save(ethnicityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ethnicityType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ethnicity-types : get all the ethnicityTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ethnicityTypes in body
     */
    @GetMapping("/ethnicity-types")
    @Timed
    public List<EthnicityType> getAllEthnicityTypes() {
        log.debug("REST request to get all EthnicityTypes");
        List<EthnicityType> ethnicityTypes = ethnicityTypeRepository.findAll();
        return ethnicityTypes;
    }

    /**
     * GET  /ethnicity-types/:id : get the "id" ethnicityType.
     *
     * @param id the id of the ethnicityType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ethnicityType, or with status 404 (Not Found)
     */
    @GetMapping("/ethnicity-types/{id}")
    @Timed
    public ResponseEntity<EthnicityType> getEthnicityType(@PathVariable Long id) {
        log.debug("REST request to get EthnicityType : {}", id);
        EthnicityType ethnicityType = ethnicityTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ethnicityType));
    }

    /**
     * DELETE  /ethnicity-types/:id : delete the "id" ethnicityType.
     *
     * @param id the id of the ethnicityType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ethnicity-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEthnicityType(@PathVariable Long id) {
        log.debug("REST request to delete EthnicityType : {}", id);
        ethnicityTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
