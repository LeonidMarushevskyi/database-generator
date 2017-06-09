package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.CriminalRecord;

import gov.ca.cwds.cals.rest.api.repository.CriminalRecordRepository;
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
 * REST controller for managing CriminalRecord.
 */
@RestController
@RequestMapping("/api")
public class CriminalRecordResource {

    private final Logger log = LoggerFactory.getLogger(CriminalRecordResource.class);

    private static final String ENTITY_NAME = "criminalRecord";
        
    private final CriminalRecordRepository criminalRecordRepository;

    public CriminalRecordResource(CriminalRecordRepository criminalRecordRepository) {
        this.criminalRecordRepository = criminalRecordRepository;
    }

    /**
     * POST  /criminal-records : Create a new criminalRecord.
     *
     * @param criminalRecord the criminalRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new criminalRecord, or with status 400 (Bad Request) if the criminalRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/criminal-records")
    @Timed
    public ResponseEntity<CriminalRecord> createCriminalRecord(@Valid @RequestBody CriminalRecord criminalRecord) throws URISyntaxException {
        log.debug("REST request to save CriminalRecord : {}", criminalRecord);
        if (criminalRecord.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new criminalRecord cannot already have an ID")).body(null);
        }
        CriminalRecord result = criminalRecordRepository.save(criminalRecord);
        return ResponseEntity.created(new URI("/api/criminal-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /criminal-records : Updates an existing criminalRecord.
     *
     * @param criminalRecord the criminalRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated criminalRecord,
     * or with status 400 (Bad Request) if the criminalRecord is not valid,
     * or with status 500 (Internal Server Error) if the criminalRecord couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/criminal-records")
    @Timed
    public ResponseEntity<CriminalRecord> updateCriminalRecord(@Valid @RequestBody CriminalRecord criminalRecord) throws URISyntaxException {
        log.debug("REST request to update CriminalRecord : {}", criminalRecord);
        if (criminalRecord.getId() == null) {
            return createCriminalRecord(criminalRecord);
        }
        CriminalRecord result = criminalRecordRepository.save(criminalRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, criminalRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /criminal-records : get all the criminalRecords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of criminalRecords in body
     */
    @GetMapping("/criminal-records")
    @Timed
    public List<CriminalRecord> getAllCriminalRecords() {
        log.debug("REST request to get all CriminalRecords");
        List<CriminalRecord> criminalRecords = criminalRecordRepository.findAll();
        return criminalRecords;
    }

    /**
     * GET  /criminal-records/:id : get the "id" criminalRecord.
     *
     * @param id the id of the criminalRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the criminalRecord, or with status 404 (Not Found)
     */
    @GetMapping("/criminal-records/{id}")
    @Timed
    public ResponseEntity<CriminalRecord> getCriminalRecord(@PathVariable Long id) {
        log.debug("REST request to get CriminalRecord : {}", id);
        CriminalRecord criminalRecord = criminalRecordRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(criminalRecord));
    }

    /**
     * DELETE  /criminal-records/:id : delete the "id" criminalRecord.
     *
     * @param id the id of the criminalRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/criminal-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteCriminalRecord(@PathVariable Long id) {
        log.debug("REST request to delete CriminalRecord : {}", id);
        criminalRecordRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
