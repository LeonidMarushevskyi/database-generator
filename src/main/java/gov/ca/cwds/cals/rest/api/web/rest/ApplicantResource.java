package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Applicant;

import gov.ca.cwds.cals.rest.api.repository.ApplicantRepository;
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
 * REST controller for managing Applicant.
 */
@RestController
@RequestMapping("/api")
public class ApplicantResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantResource.class);

    private static final String ENTITY_NAME = "applicant";
        
    private final ApplicantRepository applicantRepository;

    public ApplicantResource(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    /**
     * POST  /applicants : Create a new applicant.
     *
     * @param applicant the applicant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicant, or with status 400 (Bad Request) if the applicant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicants")
    @Timed
    public ResponseEntity<Applicant> createApplicant(@Valid @RequestBody Applicant applicant) throws URISyntaxException {
        log.debug("REST request to save Applicant : {}", applicant);
        if (applicant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new applicant cannot already have an ID")).body(null);
        }
        Applicant result = applicantRepository.save(applicant);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicants : Updates an existing applicant.
     *
     * @param applicant the applicant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicant,
     * or with status 400 (Bad Request) if the applicant is not valid,
     * or with status 500 (Internal Server Error) if the applicant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicants")
    @Timed
    public ResponseEntity<Applicant> updateApplicant(@Valid @RequestBody Applicant applicant) throws URISyntaxException {
        log.debug("REST request to update Applicant : {}", applicant);
        if (applicant.getId() == null) {
            return createApplicant(applicant);
        }
        Applicant result = applicantRepository.save(applicant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicants : get all the applicants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicants in body
     */
    @GetMapping("/applicants")
    @Timed
    public List<Applicant> getAllApplicants() {
        log.debug("REST request to get all Applicants");
        List<Applicant> applicants = applicantRepository.findAll();
        return applicants;
    }

    /**
     * GET  /applicants/:id : get the "id" applicant.
     *
     * @param id the id of the applicant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicant, or with status 404 (Not Found)
     */
    @GetMapping("/applicants/{id}")
    @Timed
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        log.debug("REST request to get Applicant : {}", id);
        Applicant applicant = applicantRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicant));
    }

    /**
     * DELETE  /applicants/:id : delete the "id" applicant.
     *
     * @param id the id of the applicant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicants/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        log.debug("REST request to delete Applicant : {}", id);
        applicantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
