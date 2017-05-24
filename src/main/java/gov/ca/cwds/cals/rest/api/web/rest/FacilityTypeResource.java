package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityType;

import gov.ca.cwds.cals.rest.api.repository.FacilityTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityTypeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityTypeMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing FacilityType.
 */
@RestController
@RequestMapping("/api")
public class FacilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(FacilityTypeResource.class);

    private static final String ENTITY_NAME = "facilityType";
        
    private final FacilityTypeRepository facilityTypeRepository;

    private final FacilityTypeMapper facilityTypeMapper;

    public FacilityTypeResource(FacilityTypeRepository facilityTypeRepository, FacilityTypeMapper facilityTypeMapper) {
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityTypeMapper = facilityTypeMapper;
    }

    /**
     * POST  /facility-types : Create a new facilityType.
     *
     * @param facilityTypeDTO the facilityTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityTypeDTO, or with status 400 (Bad Request) if the facilityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-types")
    @Timed
    public ResponseEntity<FacilityTypeDTO> createFacilityType(@Valid @RequestBody FacilityTypeDTO facilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityType : {}", facilityTypeDTO);
        if (facilityTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityType cannot already have an ID")).body(null);
        }
        FacilityType facilityType = facilityTypeMapper.facilityTypeDTOToFacilityType(facilityTypeDTO);
        facilityType = facilityTypeRepository.save(facilityType);
        FacilityTypeDTO result = facilityTypeMapper.facilityTypeToFacilityTypeDTO(facilityType);
        return ResponseEntity.created(new URI("/api/facility-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-types : Updates an existing facilityType.
     *
     * @param facilityTypeDTO the facilityTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityTypeDTO,
     * or with status 400 (Bad Request) if the facilityTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-types")
    @Timed
    public ResponseEntity<FacilityTypeDTO> updateFacilityType(@Valid @RequestBody FacilityTypeDTO facilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityType : {}", facilityTypeDTO);
        if (facilityTypeDTO.getId() == null) {
            return createFacilityType(facilityTypeDTO);
        }
        FacilityType facilityType = facilityTypeMapper.facilityTypeDTOToFacilityType(facilityTypeDTO);
        facilityType = facilityTypeRepository.save(facilityType);
        FacilityTypeDTO result = facilityTypeMapper.facilityTypeToFacilityTypeDTO(facilityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-types : get all the facilityTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilityTypes in body
     */
    @GetMapping("/facility-types")
    @Timed
    public List<FacilityTypeDTO> getAllFacilityTypes() {
        log.debug("REST request to get all FacilityTypes");
        List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
        return facilityTypeMapper.facilityTypesToFacilityTypeDTOs(facilityTypes);
    }

    /**
     * GET  /facility-types/:id : get the "id" facilityType.
     *
     * @param id the id of the facilityTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facility-types/{id}")
    @Timed
    public ResponseEntity<FacilityTypeDTO> getFacilityType(@PathVariable Long id) {
        log.debug("REST request to get FacilityType : {}", id);
        FacilityType facilityType = facilityTypeRepository.findOne(id);
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.facilityTypeToFacilityTypeDTO(facilityType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityTypeDTO));
    }

    /**
     * DELETE  /facility-types/:id : delete the "id" facilityType.
     *
     * @param id the id of the facilityTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityType(@PathVariable Long id) {
        log.debug("REST request to delete FacilityType : {}", id);
        facilityTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
