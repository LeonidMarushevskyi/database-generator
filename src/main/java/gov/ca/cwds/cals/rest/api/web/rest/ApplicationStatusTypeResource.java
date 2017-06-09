package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.ApplicationStatusType;

import gov.ca.cwds.cals.rest.api.repository.ApplicationStatusTypeRepository;
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
 * REST controller for managing ApplicationStatusType.
 */
@RestController
@RequestMapping("/api")
public class ApplicationStatusTypeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationStatusTypeResource.class);

    private static final String ENTITY_NAME = "applicationStatusType";
        
    private final ApplicationStatusTypeRepository applicationStatusTypeRepository;

    public ApplicationStatusTypeResource(ApplicationStatusTypeRepository applicationStatusTypeRepository) {
        this.applicationStatusTypeRepository = applicationStatusTypeRepository;
    }

    /**
     * POST  /application-status-types : Create a new applicationStatusType.
     *
     * @param applicationStatusType the applicationStatusType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationStatusType, or with status 400 (Bad Request) if the applicationStatusType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-status-types")
    @Timed
    public ResponseEntity<ApplicationStatusType> createApplicationStatusType(@Valid @RequestBody ApplicationStatusType applicationStatusType) throws URISyntaxException {
        log.debug("REST request to save ApplicationStatusType : {}", applicationStatusType);
        if (applicationStatusType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new applicationStatusType cannot already have an ID")).body(null);
        }
        ApplicationStatusType result = applicationStatusTypeRepository.save(applicationStatusType);
        return ResponseEntity.created(new URI("/api/application-status-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-status-types : Updates an existing applicationStatusType.
     *
     * @param applicationStatusType the applicationStatusType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationStatusType,
     * or with status 400 (Bad Request) if the applicationStatusType is not valid,
     * or with status 500 (Internal Server Error) if the applicationStatusType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-status-types")
    @Timed
    public ResponseEntity<ApplicationStatusType> updateApplicationStatusType(@Valid @RequestBody ApplicationStatusType applicationStatusType) throws URISyntaxException {
        log.debug("REST request to update ApplicationStatusType : {}", applicationStatusType);
        if (applicationStatusType.getId() == null) {
            return createApplicationStatusType(applicationStatusType);
        }
        ApplicationStatusType result = applicationStatusTypeRepository.save(applicationStatusType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationStatusType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-status-types : get all the applicationStatusTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicationStatusTypes in body
     */
    @GetMapping("/application-status-types")
    @Timed
    public List<ApplicationStatusType> getAllApplicationStatusTypes() {
        log.debug("REST request to get all ApplicationStatusTypes");
        List<ApplicationStatusType> applicationStatusTypes = applicationStatusTypeRepository.findAll();
        return applicationStatusTypes;
    }

    /**
     * GET  /application-status-types/:id : get the "id" applicationStatusType.
     *
     * @param id the id of the applicationStatusType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationStatusType, or with status 404 (Not Found)
     */
    @GetMapping("/application-status-types/{id}")
    @Timed
    public ResponseEntity<ApplicationStatusType> getApplicationStatusType(@PathVariable Long id) {
        log.debug("REST request to get ApplicationStatusType : {}", id);
        ApplicationStatusType applicationStatusType = applicationStatusTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicationStatusType));
    }

    /**
     * DELETE  /application-status-types/:id : delete the "id" applicationStatusType.
     *
     * @param id the id of the applicationStatusType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-status-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationStatusType(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationStatusType : {}", id);
        applicationStatusTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
