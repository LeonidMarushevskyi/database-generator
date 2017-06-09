package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Employment;

import gov.ca.cwds.cals.rest.api.repository.EmploymentRepository;
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
 * REST controller for managing Employment.
 */
@RestController
@RequestMapping("/api")
public class EmploymentResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentResource.class);

    private static final String ENTITY_NAME = "employment";
        
    private final EmploymentRepository employmentRepository;

    public EmploymentResource(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    /**
     * POST  /employments : Create a new employment.
     *
     * @param employment the employment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employment, or with status 400 (Bad Request) if the employment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employments")
    @Timed
    public ResponseEntity<Employment> createEmployment(@Valid @RequestBody Employment employment) throws URISyntaxException {
        log.debug("REST request to save Employment : {}", employment);
        if (employment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employment cannot already have an ID")).body(null);
        }
        Employment result = employmentRepository.save(employment);
        return ResponseEntity.created(new URI("/api/employments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employments : Updates an existing employment.
     *
     * @param employment the employment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employment,
     * or with status 400 (Bad Request) if the employment is not valid,
     * or with status 500 (Internal Server Error) if the employment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employments")
    @Timed
    public ResponseEntity<Employment> updateEmployment(@Valid @RequestBody Employment employment) throws URISyntaxException {
        log.debug("REST request to update Employment : {}", employment);
        if (employment.getId() == null) {
            return createEmployment(employment);
        }
        Employment result = employmentRepository.save(employment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employments : get all the employments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employments in body
     */
    @GetMapping("/employments")
    @Timed
    public List<Employment> getAllEmployments() {
        log.debug("REST request to get all Employments");
        List<Employment> employments = employmentRepository.findAll();
        return employments;
    }

    /**
     * GET  /employments/:id : get the "id" employment.
     *
     * @param id the id of the employment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employment, or with status 404 (Not Found)
     */
    @GetMapping("/employments/{id}")
    @Timed
    public ResponseEntity<Employment> getEmployment(@PathVariable Long id) {
        log.debug("REST request to get Employment : {}", id);
        Employment employment = employmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employment));
    }

    /**
     * DELETE  /employments/:id : delete the "id" employment.
     *
     * @param id the id of the employment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployment(@PathVariable Long id) {
        log.debug("REST request to delete Employment : {}", id);
        employmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
