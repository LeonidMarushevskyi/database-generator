package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.CountyType;

import gov.ca.cwds.cals.rest.api.repository.CountyTypeRepository;
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
 * REST controller for managing CountyType.
 */
@RestController
@RequestMapping("/api")
public class CountyTypeResource {

    private final Logger log = LoggerFactory.getLogger(CountyTypeResource.class);

    private static final String ENTITY_NAME = "countyType";
        
    private final CountyTypeRepository countyTypeRepository;

    public CountyTypeResource(CountyTypeRepository countyTypeRepository) {
        this.countyTypeRepository = countyTypeRepository;
    }

    /**
     * POST  /county-types : Create a new countyType.
     *
     * @param countyType the countyType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countyType, or with status 400 (Bad Request) if the countyType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/county-types")
    @Timed
    public ResponseEntity<CountyType> createCountyType(@Valid @RequestBody CountyType countyType) throws URISyntaxException {
        log.debug("REST request to save CountyType : {}", countyType);
        if (countyType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new countyType cannot already have an ID")).body(null);
        }
        CountyType result = countyTypeRepository.save(countyType);
        return ResponseEntity.created(new URI("/api/county-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /county-types : Updates an existing countyType.
     *
     * @param countyType the countyType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countyType,
     * or with status 400 (Bad Request) if the countyType is not valid,
     * or with status 500 (Internal Server Error) if the countyType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/county-types")
    @Timed
    public ResponseEntity<CountyType> updateCountyType(@Valid @RequestBody CountyType countyType) throws URISyntaxException {
        log.debug("REST request to update CountyType : {}", countyType);
        if (countyType.getId() == null) {
            return createCountyType(countyType);
        }
        CountyType result = countyTypeRepository.save(countyType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countyType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /county-types : get all the countyTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countyTypes in body
     */
    @GetMapping("/county-types")
    @Timed
    public List<CountyType> getAllCountyTypes() {
        log.debug("REST request to get all CountyTypes");
        List<CountyType> countyTypes = countyTypeRepository.findAll();
        return countyTypes;
    }

    /**
     * GET  /county-types/:id : get the "id" countyType.
     *
     * @param id the id of the countyType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countyType, or with status 404 (Not Found)
     */
    @GetMapping("/county-types/{id}")
    @Timed
    public ResponseEntity<CountyType> getCountyType(@PathVariable Long id) {
        log.debug("REST request to get CountyType : {}", id);
        CountyType countyType = countyTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countyType));
    }

    /**
     * DELETE  /county-types/:id : delete the "id" countyType.
     *
     * @param id the id of the countyType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/county-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountyType(@PathVariable Long id) {
        log.debug("REST request to delete CountyType : {}", id);
        countyTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
