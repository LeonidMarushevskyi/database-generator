package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityStatus;

import gov.ca.cwds.cals.rest.api.repository.FacilityStatusRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityStatusDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityStatusMapper;
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
 * REST controller for managing FacilityStatus.
 */
@RestController
@RequestMapping("/api")
public class FacilityStatusResource {

    private final Logger log = LoggerFactory.getLogger(FacilityStatusResource.class);

    private static final String ENTITY_NAME = "facilityStatus";
        
    private final FacilityStatusRepository facilityStatusRepository;

    private final FacilityStatusMapper facilityStatusMapper;

    public FacilityStatusResource(FacilityStatusRepository facilityStatusRepository, FacilityStatusMapper facilityStatusMapper) {
        this.facilityStatusRepository = facilityStatusRepository;
        this.facilityStatusMapper = facilityStatusMapper;
    }

    /**
     * POST  /facility-statuses : Create a new facilityStatus.
     *
     * @param facilityStatusDTO the facilityStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityStatusDTO, or with status 400 (Bad Request) if the facilityStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-statuses")
    @Timed
    public ResponseEntity<FacilityStatusDTO> createFacilityStatus(@Valid @RequestBody FacilityStatusDTO facilityStatusDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityStatus : {}", facilityStatusDTO);
        if (facilityStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityStatus cannot already have an ID")).body(null);
        }
        FacilityStatus facilityStatus = facilityStatusMapper.toEntity(facilityStatusDTO);
        facilityStatus = facilityStatusRepository.save(facilityStatus);
        FacilityStatusDTO result = facilityStatusMapper.toDto(facilityStatus);
        return ResponseEntity.created(new URI("/api/facility-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-statuses : Updates an existing facilityStatus.
     *
     * @param facilityStatusDTO the facilityStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityStatusDTO,
     * or with status 400 (Bad Request) if the facilityStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-statuses")
    @Timed
    public ResponseEntity<FacilityStatusDTO> updateFacilityStatus(@Valid @RequestBody FacilityStatusDTO facilityStatusDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityStatus : {}", facilityStatusDTO);
        if (facilityStatusDTO.getId() == null) {
            return createFacilityStatus(facilityStatusDTO);
        }
        FacilityStatus facilityStatus = facilityStatusMapper.toEntity(facilityStatusDTO);
        facilityStatus = facilityStatusRepository.save(facilityStatus);
        FacilityStatusDTO result = facilityStatusMapper.toDto(facilityStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-statuses : get all the facilityStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityStatuses in body
     */
    @GetMapping("/facility-statuses")
    @Timed
    public List<FacilityStatusDTO> getAllFacilityStatuses() {
        log.debug("REST request to get all FacilityStatuses");
        List<FacilityStatus> facilityStatuses = facilityStatusRepository.findAll();
        return facilityStatusMapper.toDto(facilityStatuses);
    }

    /**
     * GET  /facility-statuses/:id : get the "id" facilityStatus.
     *
     * @param id the id of the facilityStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facility-statuses/{id}")
    @Timed
    public ResponseEntity<FacilityStatusDTO> getFacilityStatus(@PathVariable Long id) {
        log.debug("REST request to get FacilityStatus : {}", id);
        FacilityStatus facilityStatus = facilityStatusRepository.findOne(id);
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(facilityStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityStatusDTO));
    }

    /**
     * DELETE  /facility-statuses/:id : delete the "id" facilityStatus.
     *
     * @param id the id of the facilityStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityStatus(@PathVariable Long id) {
        log.debug("REST request to delete FacilityStatus : {}", id);
        facilityStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
