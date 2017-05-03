package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.DistrictOffice;

import gov.ca.cwds.cals.rest.api.repository.DistrictOfficeRepository;
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
 * REST controller for managing DistrictOffice.
 */
@RestController
@RequestMapping("/api")
public class DistrictOfficeResource {

    private final Logger log = LoggerFactory.getLogger(DistrictOfficeResource.class);

    private static final String ENTITY_NAME = "districtOffice";
        
    private final DistrictOfficeRepository districtOfficeRepository;

    public DistrictOfficeResource(DistrictOfficeRepository districtOfficeRepository) {
        this.districtOfficeRepository = districtOfficeRepository;
    }

    /**
     * POST  /district-offices : Create a new districtOffice.
     *
     * @param districtOffice the districtOffice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new districtOffice, or with status 400 (Bad Request) if the districtOffice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/district-offices")
    @Timed
    public ResponseEntity<DistrictOffice> createDistrictOffice(@Valid @RequestBody DistrictOffice districtOffice) throws URISyntaxException {
        log.debug("REST request to save DistrictOffice : {}", districtOffice);
        if (districtOffice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new districtOffice cannot already have an ID")).body(null);
        }
        DistrictOffice result = districtOfficeRepository.save(districtOffice);
        return ResponseEntity.created(new URI("/api/district-offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /district-offices : Updates an existing districtOffice.
     *
     * @param districtOffice the districtOffice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated districtOffice,
     * or with status 400 (Bad Request) if the districtOffice is not valid,
     * or with status 500 (Internal Server Error) if the districtOffice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/district-offices")
    @Timed
    public ResponseEntity<DistrictOffice> updateDistrictOffice(@Valid @RequestBody DistrictOffice districtOffice) throws URISyntaxException {
        log.debug("REST request to update DistrictOffice : {}", districtOffice);
        if (districtOffice.getId() == null) {
            return createDistrictOffice(districtOffice);
        }
        DistrictOffice result = districtOfficeRepository.save(districtOffice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, districtOffice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /district-offices : get all the districtOffices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of districtOffices in body
     */
    @GetMapping("/district-offices")
    @Timed
    public List<DistrictOffice> getAllDistrictOffices() {
        log.debug("REST request to get all DistrictOffices");
        List<DistrictOffice> districtOffices = districtOfficeRepository.findAll();
        return districtOffices;
    }

    /**
     * GET  /district-offices/:id : get the "id" districtOffice.
     *
     * @param id the id of the districtOffice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the districtOffice, or with status 404 (Not Found)
     */
    @GetMapping("/district-offices/{id}")
    @Timed
    public ResponseEntity<DistrictOffice> getDistrictOffice(@PathVariable Long id) {
        log.debug("REST request to get DistrictOffice : {}", id);
        DistrictOffice districtOffice = districtOfficeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(districtOffice));
    }

    /**
     * DELETE  /district-offices/:id : delete the "id" districtOffice.
     *
     * @param id the id of the districtOffice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/district-offices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDistrictOffice(@PathVariable Long id) {
        log.debug("REST request to delete DistrictOffice : {}", id);
        districtOfficeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
