package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.BodyOfWater;

import gov.ca.cwds.cals.rest.api.repository.BodyOfWaterRepository;
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
 * REST controller for managing BodyOfWater.
 */
@RestController
@RequestMapping("/api")
public class BodyOfWaterResource {

    private final Logger log = LoggerFactory.getLogger(BodyOfWaterResource.class);

    private static final String ENTITY_NAME = "bodyOfWater";
        
    private final BodyOfWaterRepository bodyOfWaterRepository;

    public BodyOfWaterResource(BodyOfWaterRepository bodyOfWaterRepository) {
        this.bodyOfWaterRepository = bodyOfWaterRepository;
    }

    /**
     * POST  /body-of-waters : Create a new bodyOfWater.
     *
     * @param bodyOfWater the bodyOfWater to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bodyOfWater, or with status 400 (Bad Request) if the bodyOfWater has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/body-of-waters")
    @Timed
    public ResponseEntity<BodyOfWater> createBodyOfWater(@Valid @RequestBody BodyOfWater bodyOfWater) throws URISyntaxException {
        log.debug("REST request to save BodyOfWater : {}", bodyOfWater);
        if (bodyOfWater.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bodyOfWater cannot already have an ID")).body(null);
        }
        BodyOfWater result = bodyOfWaterRepository.save(bodyOfWater);
        return ResponseEntity.created(new URI("/api/body-of-waters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /body-of-waters : Updates an existing bodyOfWater.
     *
     * @param bodyOfWater the bodyOfWater to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bodyOfWater,
     * or with status 400 (Bad Request) if the bodyOfWater is not valid,
     * or with status 500 (Internal Server Error) if the bodyOfWater couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/body-of-waters")
    @Timed
    public ResponseEntity<BodyOfWater> updateBodyOfWater(@Valid @RequestBody BodyOfWater bodyOfWater) throws URISyntaxException {
        log.debug("REST request to update BodyOfWater : {}", bodyOfWater);
        if (bodyOfWater.getId() == null) {
            return createBodyOfWater(bodyOfWater);
        }
        BodyOfWater result = bodyOfWaterRepository.save(bodyOfWater);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bodyOfWater.getId().toString()))
            .body(result);
    }

    /**
     * GET  /body-of-waters : get all the bodyOfWaters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bodyOfWaters in body
     */
    @GetMapping("/body-of-waters")
    @Timed
    public List<BodyOfWater> getAllBodyOfWaters() {
        log.debug("REST request to get all BodyOfWaters");
        List<BodyOfWater> bodyOfWaters = bodyOfWaterRepository.findAll();
        return bodyOfWaters;
    }

    /**
     * GET  /body-of-waters/:id : get the "id" bodyOfWater.
     *
     * @param id the id of the bodyOfWater to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bodyOfWater, or with status 404 (Not Found)
     */
    @GetMapping("/body-of-waters/{id}")
    @Timed
    public ResponseEntity<BodyOfWater> getBodyOfWater(@PathVariable Long id) {
        log.debug("REST request to get BodyOfWater : {}", id);
        BodyOfWater bodyOfWater = bodyOfWaterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bodyOfWater));
    }

    /**
     * DELETE  /body-of-waters/:id : delete the "id" bodyOfWater.
     *
     * @param id the id of the bodyOfWater to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/body-of-waters/{id}")
    @Timed
    public ResponseEntity<Void> deleteBodyOfWater(@PathVariable Long id) {
        log.debug("REST request to delete BodyOfWater : {}", id);
        bodyOfWaterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
