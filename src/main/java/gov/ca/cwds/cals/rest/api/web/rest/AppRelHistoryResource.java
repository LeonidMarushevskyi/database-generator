package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.AppRelHistory;

import gov.ca.cwds.cals.rest.api.repository.AppRelHistoryRepository;
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
 * REST controller for managing AppRelHistory.
 */
@RestController
@RequestMapping("/api")
public class AppRelHistoryResource {

    private final Logger log = LoggerFactory.getLogger(AppRelHistoryResource.class);

    private static final String ENTITY_NAME = "appRelHistory";
        
    private final AppRelHistoryRepository appRelHistoryRepository;

    public AppRelHistoryResource(AppRelHistoryRepository appRelHistoryRepository) {
        this.appRelHistoryRepository = appRelHistoryRepository;
    }

    /**
     * POST  /app-rel-histories : Create a new appRelHistory.
     *
     * @param appRelHistory the appRelHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appRelHistory, or with status 400 (Bad Request) if the appRelHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/app-rel-histories")
    @Timed
    public ResponseEntity<AppRelHistory> createAppRelHistory(@Valid @RequestBody AppRelHistory appRelHistory) throws URISyntaxException {
        log.debug("REST request to save AppRelHistory : {}", appRelHistory);
        if (appRelHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appRelHistory cannot already have an ID")).body(null);
        }
        AppRelHistory result = appRelHistoryRepository.save(appRelHistory);
        return ResponseEntity.created(new URI("/api/app-rel-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app-rel-histories : Updates an existing appRelHistory.
     *
     * @param appRelHistory the appRelHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appRelHistory,
     * or with status 400 (Bad Request) if the appRelHistory is not valid,
     * or with status 500 (Internal Server Error) if the appRelHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/app-rel-histories")
    @Timed
    public ResponseEntity<AppRelHistory> updateAppRelHistory(@Valid @RequestBody AppRelHistory appRelHistory) throws URISyntaxException {
        log.debug("REST request to update AppRelHistory : {}", appRelHistory);
        if (appRelHistory.getId() == null) {
            return createAppRelHistory(appRelHistory);
        }
        AppRelHistory result = appRelHistoryRepository.save(appRelHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appRelHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app-rel-histories : get all the appRelHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appRelHistories in body
     */
    @GetMapping("/app-rel-histories")
    @Timed
    public List<AppRelHistory> getAllAppRelHistories() {
        log.debug("REST request to get all AppRelHistories");
        List<AppRelHistory> appRelHistories = appRelHistoryRepository.findAll();
        return appRelHistories;
    }

    /**
     * GET  /app-rel-histories/:id : get the "id" appRelHistory.
     *
     * @param id the id of the appRelHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appRelHistory, or with status 404 (Not Found)
     */
    @GetMapping("/app-rel-histories/{id}")
    @Timed
    public ResponseEntity<AppRelHistory> getAppRelHistory(@PathVariable Long id) {
        log.debug("REST request to get AppRelHistory : {}", id);
        AppRelHistory appRelHistory = appRelHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appRelHistory));
    }

    /**
     * DELETE  /app-rel-histories/:id : delete the "id" appRelHistory.
     *
     * @param id the id of the appRelHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/app-rel-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppRelHistory(@PathVariable Long id) {
        log.debug("REST request to delete AppRelHistory : {}", id);
        appRelHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
