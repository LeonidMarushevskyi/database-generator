package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.AgeGroupType;

import gov.ca.cwds.cals.rest.api.repository.AgeGroupTypeRepository;
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
 * REST controller for managing AgeGroupType.
 */
@RestController
@RequestMapping("/api")
public class AgeGroupTypeResource {

    private final Logger log = LoggerFactory.getLogger(AgeGroupTypeResource.class);

    private static final String ENTITY_NAME = "ageGroupType";
        
    private final AgeGroupTypeRepository ageGroupTypeRepository;

    public AgeGroupTypeResource(AgeGroupTypeRepository ageGroupTypeRepository) {
        this.ageGroupTypeRepository = ageGroupTypeRepository;
    }

    /**
     * POST  /age-group-types : Create a new ageGroupType.
     *
     * @param ageGroupType the ageGroupType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ageGroupType, or with status 400 (Bad Request) if the ageGroupType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/age-group-types")
    @Timed
    public ResponseEntity<AgeGroupType> createAgeGroupType(@Valid @RequestBody AgeGroupType ageGroupType) throws URISyntaxException {
        log.debug("REST request to save AgeGroupType : {}", ageGroupType);
        if (ageGroupType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ageGroupType cannot already have an ID")).body(null);
        }
        AgeGroupType result = ageGroupTypeRepository.save(ageGroupType);
        return ResponseEntity.created(new URI("/api/age-group-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /age-group-types : Updates an existing ageGroupType.
     *
     * @param ageGroupType the ageGroupType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ageGroupType,
     * or with status 400 (Bad Request) if the ageGroupType is not valid,
     * or with status 500 (Internal Server Error) if the ageGroupType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/age-group-types")
    @Timed
    public ResponseEntity<AgeGroupType> updateAgeGroupType(@Valid @RequestBody AgeGroupType ageGroupType) throws URISyntaxException {
        log.debug("REST request to update AgeGroupType : {}", ageGroupType);
        if (ageGroupType.getId() == null) {
            return createAgeGroupType(ageGroupType);
        }
        AgeGroupType result = ageGroupTypeRepository.save(ageGroupType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ageGroupType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /age-group-types : get all the ageGroupTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ageGroupTypes in body
     */
    @GetMapping("/age-group-types")
    @Timed
    public List<AgeGroupType> getAllAgeGroupTypes() {
        log.debug("REST request to get all AgeGroupTypes");
        List<AgeGroupType> ageGroupTypes = ageGroupTypeRepository.findAll();
        return ageGroupTypes;
    }

    /**
     * GET  /age-group-types/:id : get the "id" ageGroupType.
     *
     * @param id the id of the ageGroupType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ageGroupType, or with status 404 (Not Found)
     */
    @GetMapping("/age-group-types/{id}")
    @Timed
    public ResponseEntity<AgeGroupType> getAgeGroupType(@PathVariable Long id) {
        log.debug("REST request to get AgeGroupType : {}", id);
        AgeGroupType ageGroupType = ageGroupTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ageGroupType));
    }

    /**
     * DELETE  /age-group-types/:id : delete the "id" ageGroupType.
     *
     * @param id the id of the ageGroupType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/age-group-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgeGroupType(@PathVariable Long id) {
        log.debug("REST request to delete AgeGroupType : {}", id);
        ageGroupTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
