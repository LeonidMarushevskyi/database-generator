package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityAddress;

import gov.ca.cwds.cals.rest.api.repository.FacilityAddressRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityAddressDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityAddressMapper;
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
 * REST controller for managing FacilityAddress.
 */
@RestController
@RequestMapping("/api")
public class FacilityAddressResource {

    private final Logger log = LoggerFactory.getLogger(FacilityAddressResource.class);

    private static final String ENTITY_NAME = "facilityAddress";
        
    private final FacilityAddressRepository facilityAddressRepository;

    private final FacilityAddressMapper facilityAddressMapper;

    public FacilityAddressResource(FacilityAddressRepository facilityAddressRepository, FacilityAddressMapper facilityAddressMapper) {
        this.facilityAddressRepository = facilityAddressRepository;
        this.facilityAddressMapper = facilityAddressMapper;
    }

    /**
     * POST  /facility-addresses : Create a new facilityAddress.
     *
     * @param facilityAddressDTO the facilityAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityAddressDTO, or with status 400 (Bad Request) if the facilityAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-addresses")
    @Timed
    public ResponseEntity<FacilityAddressDTO> createFacilityAddress(@RequestBody FacilityAddressDTO facilityAddressDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityAddress : {}", facilityAddressDTO);
        if (facilityAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityAddress cannot already have an ID")).body(null);
        }
        FacilityAddress facilityAddress = facilityAddressMapper.toEntity(facilityAddressDTO);
        facilityAddress = facilityAddressRepository.save(facilityAddress);
        FacilityAddressDTO result = facilityAddressMapper.toDto(facilityAddress);
        return ResponseEntity.created(new URI("/api/facility-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-addresses : Updates an existing facilityAddress.
     *
     * @param facilityAddressDTO the facilityAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityAddressDTO,
     * or with status 400 (Bad Request) if the facilityAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-addresses")
    @Timed
    public ResponseEntity<FacilityAddressDTO> updateFacilityAddress(@RequestBody FacilityAddressDTO facilityAddressDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityAddress : {}", facilityAddressDTO);
        if (facilityAddressDTO.getId() == null) {
            return createFacilityAddress(facilityAddressDTO);
        }
        FacilityAddress facilityAddress = facilityAddressMapper.toEntity(facilityAddressDTO);
        facilityAddress = facilityAddressRepository.save(facilityAddress);
        FacilityAddressDTO result = facilityAddressMapper.toDto(facilityAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-addresses : get all the facilityAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityAddresses in body
     */
    @GetMapping("/facility-addresses")
    @Timed
    public List<FacilityAddressDTO> getAllFacilityAddresses() {
        log.debug("REST request to get all FacilityAddresses");
        List<FacilityAddress> facilityAddresses = facilityAddressRepository.findAll();
        return facilityAddressMapper.toDto(facilityAddresses);
    }

    /**
     * GET  /facility-addresses/:id : get the "id" facilityAddress.
     *
     * @param id the id of the facilityAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facility-addresses/{id}")
    @Timed
    public ResponseEntity<FacilityAddressDTO> getFacilityAddress(@PathVariable Long id) {
        log.debug("REST request to get FacilityAddress : {}", id);
        FacilityAddress facilityAddress = facilityAddressRepository.findOne(id);
        FacilityAddressDTO facilityAddressDTO = facilityAddressMapper.toDto(facilityAddress);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityAddressDTO));
    }

    /**
     * DELETE  /facility-addresses/:id : delete the "id" facilityAddress.
     *
     * @param id the id of the facilityAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityAddress(@PathVariable Long id) {
        log.debug("REST request to delete FacilityAddress : {}", id);
        facilityAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
