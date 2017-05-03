package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.County;

import gov.ca.cwds.cals.rest.api.repository.CountyRepository;
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
 * REST controller for managing County.
 */
@RestController
@RequestMapping("/api")
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);

    private static final String ENTITY_NAME = "county";
        
    private final CountyRepository countyRepository;

    public CountyResource(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }

    /**
     * POST  /counties : Create a new county.
     *
     * @param county the county to create
     * @return the ResponseEntity with status 201 (Created) and with body the new county, or with status 400 (Bad Request) if the county has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/counties")
    @Timed
    public ResponseEntity<County> createCounty(@Valid @RequestBody County county) throws URISyntaxException {
        log.debug("REST request to save County : {}", county);
        if (county.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new county cannot already have an ID")).body(null);
        }
        County result = countyRepository.save(county);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /counties : Updates an existing county.
     *
     * @param county the county to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated county,
     * or with status 400 (Bad Request) if the county is not valid,
     * or with status 500 (Internal Server Error) if the county couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/counties")
    @Timed
    public ResponseEntity<County> updateCounty(@Valid @RequestBody County county) throws URISyntaxException {
        log.debug("REST request to update County : {}", county);
        if (county.getId() == null) {
            return createCounty(county);
        }
        County result = countyRepository.save(county);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, county.getId().toString()))
            .body(result);
    }

    /**
     * GET  /counties : get all the counties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of counties in body
     */
    @GetMapping("/counties")
    @Timed
    public List<County> getAllCounties() {
        log.debug("REST request to get all Counties");
        List<County> counties = countyRepository.findAll();
        return counties;
    }

    /**
     * GET  /counties/:id : get the "id" county.
     *
     * @param id the id of the county to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the county, or with status 404 (Not Found)
     */
    @GetMapping("/counties/{id}")
    @Timed
    public ResponseEntity<County> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        County county = countyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(county));
    }

    /**
     * DELETE  /counties/:id : delete the "id" county.
     *
     * @param id the id of the county to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/counties/{id}")
    @Timed
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
