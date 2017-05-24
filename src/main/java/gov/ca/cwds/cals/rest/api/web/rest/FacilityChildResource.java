package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.FacilityChild;

import gov.ca.cwds.cals.rest.api.repository.FacilityChildRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.web.rest.util.PaginationUtil;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityChildDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityChildMapper;
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
 * REST controller for managing FacilityChild.
 */
@RestController
@RequestMapping("/api")
public class FacilityChildResource {

    private final Logger log = LoggerFactory.getLogger(FacilityChildResource.class);

    private static final String ENTITY_NAME = "facilityChild";
        
    private final FacilityChildRepository facilityChildRepository;

    private final FacilityChildMapper facilityChildMapper;

    public FacilityChildResource(FacilityChildRepository facilityChildRepository, FacilityChildMapper facilityChildMapper) {
        this.facilityChildRepository = facilityChildRepository;
        this.facilityChildMapper = facilityChildMapper;
    }

    /**
     * POST  /facility-children : Create a new facilityChild.
     *
     * @param facilityChildDTO the facilityChildDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityChildDTO, or with status 400 (Bad Request) if the facilityChild has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facility-children")
    @Timed
    public ResponseEntity<FacilityChildDTO> createFacilityChild(@Valid @RequestBody FacilityChildDTO facilityChildDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityChild : {}", facilityChildDTO);
        if (facilityChildDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facilityChild cannot already have an ID")).body(null);
        }
        FacilityChild facilityChild = facilityChildMapper.facilityChildDTOToFacilityChild(facilityChildDTO);
        facilityChild = facilityChildRepository.save(facilityChild);
        FacilityChildDTO result = facilityChildMapper.facilityChildToFacilityChildDTO(facilityChild);
        return ResponseEntity.created(new URI("/api/facility-children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-children : Updates an existing facilityChild.
     *
     * @param facilityChildDTO the facilityChildDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityChildDTO,
     * or with status 400 (Bad Request) if the facilityChildDTO is not valid,
     * or with status 500 (Internal Server Error) if the facilityChildDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facility-children")
    @Timed
    public ResponseEntity<FacilityChildDTO> updateFacilityChild(@Valid @RequestBody FacilityChildDTO facilityChildDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityChild : {}", facilityChildDTO);
        if (facilityChildDTO.getId() == null) {
            return createFacilityChild(facilityChildDTO);
        }
        FacilityChild facilityChild = facilityChildMapper.facilityChildDTOToFacilityChild(facilityChildDTO);
        facilityChild = facilityChildRepository.save(facilityChild);
        FacilityChildDTO result = facilityChildMapper.facilityChildToFacilityChildDTO(facilityChild);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facilityChildDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-children : get all the facilityChildren.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityChildren in body
     */
    @GetMapping("/facility-children")
    @Timed
    public ResponseEntity<List<FacilityChildDTO>> getAllFacilityChildren(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FacilityChildren");
        Page<FacilityChild> page = facilityChildRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facility-children");
        return new ResponseEntity<>(facilityChildMapper.facilityChildrenToFacilityChildDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-children/:id : get the "id" facilityChild.
     *
     * @param id the id of the facilityChildDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityChildDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facility-children/{id}")
    @Timed
    public ResponseEntity<FacilityChildDTO> getFacilityChild(@PathVariable Long id) {
        log.debug("REST request to get FacilityChild : {}", id);
        FacilityChild facilityChild = facilityChildRepository.findOne(id);
        FacilityChildDTO facilityChildDTO = facilityChildMapper.facilityChildToFacilityChildDTO(facilityChild);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facilityChildDTO));
    }

    /**
     * DELETE  /facility-children/:id : delete the "id" facilityChild.
     *
     * @param id the id of the facilityChildDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facility-children/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacilityChild(@PathVariable Long id) {
        log.debug("REST request to delete FacilityChild : {}", id);
        facilityChildRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
