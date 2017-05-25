package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityPhone;

import gov.ca.cwds.cals.rest.api.repository.FacilityPhoneRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityPhoneDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityPhoneMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FacilityPhone.
 */
@RestController
@RequestMapping("/api")
public class FacilityPhoneResource {

    private final Logger log = LoggerFactory.getLogger(FacilityPhoneResource.class);

    private static final String ENTITY_NAME = "facilityPhone";
        
    private final FacilityPhoneRepository facilityPhoneRepository;

    private final FacilityPhoneMapper facilityPhoneMapper;

    public FacilityPhoneResource(FacilityPhoneRepository facilityPhoneRepository, FacilityPhoneMapper facilityPhoneMapper) {
        this.facilityPhoneRepository = facilityPhoneRepository;
        this.facilityPhoneMapper = facilityPhoneMapper;
    }

    /**
     * POST  /facility-phones : Create a new facilityPhone.
     *
     * @param facilityPhoneDTO the facilityPhoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityPhoneDTO, or with status 400 (Bad Request) if the facilityPhone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-phones")
    @Timed
    public ResponseEntity<FacilityPhoneDTO> createFacilityPhone(@RequestBody FacilityPhoneDTO facilityPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityPhone : {}", facilityPhoneDTO);
        if (facilityPhoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityPhone cannot already have an ID")).body(null);
        }
        FacilityPhone facilityPhone = facilityPhoneMapper.toEntity(facilityPhoneDTO);
        facilityPhone = facilityPhoneRepository.save(facilityPhone);
        FacilityPhoneDTO result = facilityPhoneMapper.toDto(facilityPhone);
        return ResponseEntity.created(new URI("/api/facility-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-phones : Updates an existing facilityPhone.
     *
     * @param facilityPhoneDTO the facilityPhoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityPhoneDTO,
     * or with status 400 (Bad Request) if the facilityPhoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityPhoneDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-phones")
    @Timed
    public ResponseEntity<FacilityPhoneDTO> updateFacilityPhone(@RequestBody FacilityPhoneDTO facilityPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityPhone : {}", facilityPhoneDTO);
        if (facilityPhoneDTO.getId() == null) {
            return createFacilityPhone(facilityPhoneDTO);
        }
        FacilityPhone facilityPhone = facilityPhoneMapper.toEntity(facilityPhoneDTO);
        facilityPhone = facilityPhoneRepository.save(facilityPhone);
        FacilityPhoneDTO result = facilityPhoneMapper.toDto(facilityPhone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-phones : get all the facilityPhones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityPhones in body
     */
    @GetMapping("/facility-phones")
    @Timed
    public List<FacilityPhoneDTO> getAllFacilityPhones() {
        log.debug("REST request to get all FacilityPhones");
        List<FacilityPhone> facilityPhones = facilityPhoneRepository.findAll();
        return facilityPhoneMapper.toDto(facilityPhones);
    }

    /**
     * GET  /facility-phones/:id : get the "id" facilityPhone.
     *
     * @param id the id of the facilityPhoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityPhoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facility-phones/{id}")
    @Timed
    public ResponseEntity<FacilityPhoneDTO> getFacilityPhone(@PathVariable Long id) {
        log.debug("REST request to get FacilityPhone : {}", id);
        FacilityPhone facilityPhone = facilityPhoneRepository.findOne(id);
        FacilityPhoneDTO facilityPhoneDTO = facilityPhoneMapper.toDto(facilityPhone);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityPhoneDTO));
    }

    /**
     * DELETE  /facility-phones/:id : delete the "id" facilityPhone.
     *
     * @param id the id of the facilityPhoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-phones/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityPhone(@PathVariable Long id) {
        log.debug("REST request to delete FacilityPhone : {}", id);
        facilityPhoneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
