package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.LicensureHistory;

import gov.ca.cwds.cals.rest.api.repository.LicensureHistoryRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing LicensureHistory.
 */
@RestController
@RequestMapping("/api")
public class LicensureHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LicensureHistoryResource.class);

    private static final String ENTITY_NAME = "licensureHistory";
        
    private final LicensureHistoryRepository licensureHistoryRepository;

    public LicensureHistoryResource(LicensureHistoryRepository licensureHistoryRepository) {
        this.licensureHistoryRepository = licensureHistoryRepository;
    }

    /**
     * POST  /licensure-histories : Create a new licensureHistory.
     *
     * @param licensureHistory the licensureHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new licensureHistory, or with status 400 (Bad Request) if the licensureHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/licensure-histories")
    @Timed
    public ResponseEntity<LicensureHistory> createLicensureHistory(@Valid @RequestBody LicensureHistory licensureHistory) throws URISyntaxException {
        log.debug("REST request to save LicensureHistory : {}", licensureHistory);
        if (licensureHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new licensureHistory cannot already have an ID")).body(null);
        }
        LicensureHistory result = licensureHistoryRepository.save(licensureHistory);
        return ResponseEntity.created(new URI("/api/licensure-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /licensure-histories : Updates an existing licensureHistory.
     *
     * @param licensureHistory the licensureHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated licensureHistory,
     * or with status 400 (Bad Request) if the licensureHistory is not valid,
     * or with status 500 (Internal Server Error) if the licensureHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/licensure-histories")
    @Timed
    public ResponseEntity<LicensureHistory> updateLicensureHistory(@Valid @RequestBody LicensureHistory licensureHistory) throws URISyntaxException {
        log.debug("REST request to update LicensureHistory : {}", licensureHistory);
        if (licensureHistory.getId() == null) {
            return createLicensureHistory(licensureHistory);
        }
        LicensureHistory result = licensureHistoryRepository.save(licensureHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, licensureHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /licensure-histories : get all the licensureHistories.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of licensureHistories in body
     */
    @GetMapping("/licensure-histories")
    @Timed
    public List<LicensureHistory> getAllLicensureHistories(@RequestParam(required = false) String filter) {
        if ("application-is-null".equals(filter)) {
            log.debug("REST request to get all LicensureHistorys where application is null");
            return StreamSupport
                .stream(licensureHistoryRepository.findAll().spliterator(), false)
                .filter(licensureHistory -> licensureHistory.getApplication() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all LicensureHistories");
        List<LicensureHistory> licensureHistories = licensureHistoryRepository.findAll();
        return licensureHistories;
    }

    /**
     * GET  /licensure-histories/:id : get the "id" licensureHistory.
     *
     * @param id the id of the licensureHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the licensureHistory, or with status 404 (Not Found)
     */
    @GetMapping("/licensure-histories/{id}")
    @Timed
    public ResponseEntity<LicensureHistory> getLicensureHistory(@PathVariable Long id) {
        log.debug("REST request to get LicensureHistory : {}", id);
        LicensureHistory licensureHistory = licensureHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(licensureHistory));
    }

    /**
     * DELETE  /licensure-histories/:id : delete the "id" licensureHistory.
     *
     * @param id the id of the licensureHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/licensure-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteLicensureHistory(@PathVariable Long id) {
        log.debug("REST request to delete LicensureHistory : {}", id);
        licensureHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
