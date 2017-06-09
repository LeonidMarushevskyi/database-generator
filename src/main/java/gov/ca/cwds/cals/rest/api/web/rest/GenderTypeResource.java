package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.GenderType;

import gov.ca.cwds.cals.rest.api.repository.GenderTypeRepository;
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
 * REST controller for managing GenderType.
 */
@RestController
@RequestMapping("/api")
public class GenderTypeResource {

    private final Logger log = LoggerFactory.getLogger(GenderTypeResource.class);

    private static final String ENTITY_NAME = "genderType";
        
    private final GenderTypeRepository genderTypeRepository;

    public GenderTypeResource(GenderTypeRepository genderTypeRepository) {
        this.genderTypeRepository = genderTypeRepository;
    }

    /**
     * POST  /gender-types : Create a new genderType.
     *
     * @param genderType the genderType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genderType, or with status 400 (Bad Request) if the genderType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gender-types")
    @Timed
    public ResponseEntity<GenderType> createGenderType(@Valid @RequestBody GenderType genderType) throws URISyntaxException {
        log.debug("REST request to save GenderType : {}", genderType);
        if (genderType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new genderType cannot already have an ID")).body(null);
        }
        GenderType result = genderTypeRepository.save(genderType);
        return ResponseEntity.created(new URI("/api/gender-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gender-types : Updates an existing genderType.
     *
     * @param genderType the genderType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genderType,
     * or with status 400 (Bad Request) if the genderType is not valid,
     * or with status 500 (Internal Server Error) if the genderType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gender-types")
    @Timed
    public ResponseEntity<GenderType> updateGenderType(@Valid @RequestBody GenderType genderType) throws URISyntaxException {
        log.debug("REST request to update GenderType : {}", genderType);
        if (genderType.getId() == null) {
            return createGenderType(genderType);
        }
        GenderType result = genderTypeRepository.save(genderType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genderType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gender-types : get all the genderTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genderTypes in body
     */
    @GetMapping("/gender-types")
    @Timed
    public List<GenderType> getAllGenderTypes() {
        log.debug("REST request to get all GenderTypes");
        List<GenderType> genderTypes = genderTypeRepository.findAll();
        return genderTypes;
    }

    /**
     * GET  /gender-types/:id : get the "id" genderType.
     *
     * @param id the id of the genderType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genderType, or with status 404 (Not Found)
     */
    @GetMapping("/gender-types/{id}")
    @Timed
    public ResponseEntity<GenderType> getGenderType(@PathVariable Long id) {
        log.debug("REST request to get GenderType : {}", id);
        GenderType genderType = genderTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genderType));
    }

    /**
     * DELETE  /gender-types/:id : delete the "id" genderType.
     *
     * @param id the id of the genderType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gender-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenderType(@PathVariable Long id) {
        log.debug("REST request to delete GenderType : {}", id);
        genderTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
