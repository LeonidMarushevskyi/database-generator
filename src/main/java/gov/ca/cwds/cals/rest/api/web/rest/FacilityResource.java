package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Facility;

import gov.ca.cwds.cals.rest.api.repository.FacilityRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.web.rest.util.PaginationUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Facility.
 */
@RestController
@RequestMapping("/api")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(FacilityResource.class);

    private static final String ENTITY_NAME = "facility";
        
    private final FacilityRepository facilityRepository;

    private final FacilityMapper facilityMapper;

    public FacilityResource(FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
    }

    /**
     * POST  /facilities : Create a new facility.
     *
     * @param facilityDTO the facilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityDTO, or with status 400 (Bad Request) if the facility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facilities")
    @Timed
    public ResponseEntity<FacilityDTO> createFacility(@Valid @RequestBody FacilityDTO facilityDTO) throws URISyntaxException {
        log.debug("REST request to save Facility : {}", facilityDTO);
        if (facilityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facility cannot already have an ID")).body(null);
        }
        Facility facility = facilityMapper.facilityDTOToFacility(facilityDTO);
        facility = facilityRepository.save(facility);
        FacilityDTO result = facilityMapper.facilityToFacilityDTO(facility);
        return ResponseEntity.created(new URI("/api/facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facilities : Updates an existing facility.
     *
     * @param facilityDTO the facilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityDTO,
     * or with status 400 (Bad Request) if the facilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facilities")
    @Timed
    public ResponseEntity<FacilityDTO> updateFacility(@Valid @RequestBody FacilityDTO facilityDTO) throws URISyntaxException {
        log.debug("REST request to update Facility : {}", facilityDTO);
        if (facilityDTO.getId() == null) {
            return createFacility(facilityDTO);
        }
        Facility facility = facilityMapper.facilityDTOToFacility(facilityDTO);
        facility = facilityRepository.save(facility);
        FacilityDTO result = facilityMapper.facilityToFacilityDTO(facility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facilities : get all the facilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilities in body
     */
    @GetMapping("/facilities")
    @Timed
    public ResponseEntity<List<FacilityDTO>> getAllFacilities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Facilities");
        Page<Facility> page = facilityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facilities");
        return new ResponseEntity<>(facilityMapper.facilitiesToFacilityDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /facilities/:id : get the "id" facility.
     *
     * @param id the id of the facilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<FacilityDTO> getFacility(@PathVariable Long id) {
        log.debug("REST request to get Facility : {}", id);
        Facility facility = facilityRepository.findOne(id);
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityDTO));
    }

    /**
     * DELETE  /facilities/:id : delete the "id" facility.
     *
     * @param id the id of the facilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("REST request to delete Facility : {}", id);
        facilityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
