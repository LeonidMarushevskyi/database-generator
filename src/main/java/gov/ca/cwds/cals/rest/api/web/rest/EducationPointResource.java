package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.EducationPoint;

import gov.ca.cwds.cals.rest.api.repository.EducationPointRepository;
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
 * REST controller for managing EducationPoint.
 */
@RestController
@RequestMapping("/api")
public class EducationPointResource {

    private final Logger log = LoggerFactory.getLogger(EducationPointResource.class);

    private static final String ENTITY_NAME = "educationPoint";
        
    private final EducationPointRepository educationPointRepository;

    public EducationPointResource(EducationPointRepository educationPointRepository) {
        this.educationPointRepository = educationPointRepository;
    }

    /**
     * POST  /education-points : Create a new educationPoint.
     *
     * @param educationPoint the educationPoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationPoint, or with status 400 (Bad Request) if the educationPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/education-points")
    @Timed
    public ResponseEntity<EducationPoint> createEducationPoint(@Valid @RequestBody EducationPoint educationPoint) throws URISyntaxException {
        log.debug("REST request to save EducationPoint : {}", educationPoint);
        if (educationPoint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new educationPoint cannot already have an ID")).body(null);
        }
        EducationPoint result = educationPointRepository.save(educationPoint);
        return ResponseEntity.created(new URI("/api/education-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education-points : Updates an existing educationPoint.
     *
     * @param educationPoint the educationPoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationPoint,
     * or with status 400 (Bad Request) if the educationPoint is not valid,
     * or with status 500 (Internal Server Error) if the educationPoint couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/education-points")
    @Timed
    public ResponseEntity<EducationPoint> updateEducationPoint(@Valid @RequestBody EducationPoint educationPoint) throws URISyntaxException {
        log.debug("REST request to update EducationPoint : {}", educationPoint);
        if (educationPoint.getId() == null) {
            return createEducationPoint(educationPoint);
        }
        EducationPoint result = educationPointRepository.save(educationPoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationPoint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education-points : get all the educationPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of educationPoints in body
     */
    @GetMapping("/education-points")
    @Timed
    public List<EducationPoint> getAllEducationPoints() {
        log.debug("REST request to get all EducationPoints");
        List<EducationPoint> educationPoints = educationPointRepository.findAll();
        return educationPoints;
    }

    /**
     * GET  /education-points/:id : get the "id" educationPoint.
     *
     * @param id the id of the educationPoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationPoint, or with status 404 (Not Found)
     */
    @GetMapping("/education-points/{id}")
    @Timed
    public ResponseEntity<EducationPoint> getEducationPoint(@PathVariable Long id) {
        log.debug("REST request to get EducationPoint : {}", id);
        EducationPoint educationPoint = educationPointRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(educationPoint));
    }

    /**
     * DELETE  /education-points/:id : delete the "id" educationPoint.
     *
     * @param id the id of the educationPoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/education-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationPoint(@PathVariable Long id) {
        log.debug("REST request to delete EducationPoint : {}", id);
        educationPointRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
