package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.EducationLevelType;

import gov.ca.cwds.cals.rest.api.repository.EducationLevelTypeRepository;
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
 * REST controller for managing EducationLevelType.
 */
@RestController
@RequestMapping("/api")
public class EducationLevelTypeResource {

    private final Logger log = LoggerFactory.getLogger(EducationLevelTypeResource.class);

    private static final String ENTITY_NAME = "educationLevelType";
        
    private final EducationLevelTypeRepository educationLevelTypeRepository;

    public EducationLevelTypeResource(EducationLevelTypeRepository educationLevelTypeRepository) {
        this.educationLevelTypeRepository = educationLevelTypeRepository;
    }

    /**
     * POST  /education-level-types : Create a new educationLevelType.
     *
     * @param educationLevelType the educationLevelType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationLevelType, or with status 400 (Bad Request) if the educationLevelType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/education-level-types")
    @Timed
    public ResponseEntity<EducationLevelType> createEducationLevelType(@Valid @RequestBody EducationLevelType educationLevelType) throws URISyntaxException {
        log.debug("REST request to save EducationLevelType : {}", educationLevelType);
        if (educationLevelType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new educationLevelType cannot already have an ID")).body(null);
        }
        EducationLevelType result = educationLevelTypeRepository.save(educationLevelType);
        return ResponseEntity.created(new URI("/api/education-level-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education-level-types : Updates an existing educationLevelType.
     *
     * @param educationLevelType the educationLevelType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationLevelType,
     * or with status 400 (Bad Request) if the educationLevelType is not valid,
     * or with status 500 (Internal Server Error) if the educationLevelType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/education-level-types")
    @Timed
    public ResponseEntity<EducationLevelType> updateEducationLevelType(@Valid @RequestBody EducationLevelType educationLevelType) throws URISyntaxException {
        log.debug("REST request to update EducationLevelType : {}", educationLevelType);
        if (educationLevelType.getId() == null) {
            return createEducationLevelType(educationLevelType);
        }
        EducationLevelType result = educationLevelTypeRepository.save(educationLevelType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationLevelType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education-level-types : get all the educationLevelTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of educationLevelTypes in body
     */
    @GetMapping("/education-level-types")
    @Timed
    public List<EducationLevelType> getAllEducationLevelTypes() {
        log.debug("REST request to get all EducationLevelTypes");
        List<EducationLevelType> educationLevelTypes = educationLevelTypeRepository.findAll();
        return educationLevelTypes;
    }

    /**
     * GET  /education-level-types/:id : get the "id" educationLevelType.
     *
     * @param id the id of the educationLevelType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationLevelType, or with status 404 (Not Found)
     */
    @GetMapping("/education-level-types/{id}")
    @Timed
    public ResponseEntity<EducationLevelType> getEducationLevelType(@PathVariable Long id) {
        log.debug("REST request to get EducationLevelType : {}", id);
        EducationLevelType educationLevelType = educationLevelTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(educationLevelType));
    }

    /**
     * DELETE  /education-level-types/:id : delete the "id" educationLevelType.
     *
     * @param id the id of the educationLevelType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/education-level-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationLevelType(@PathVariable Long id) {
        log.debug("REST request to delete EducationLevelType : {}", id);
        educationLevelTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
