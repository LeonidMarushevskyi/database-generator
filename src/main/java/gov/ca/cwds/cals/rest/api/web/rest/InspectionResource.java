package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Inspection;

import gov.ca.cwds.cals.rest.api.repository.InspectionRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.InspectionDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.InspectionMapper;
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
 * REST controller for managing Inspection.
 */
@RestController
@RequestMapping("/api")
public class InspectionResource {

    private final Logger log = LoggerFactory.getLogger(InspectionResource.class);

    private static final String ENTITY_NAME = "inspection";
        
    private final InspectionRepository inspectionRepository;

    private final InspectionMapper inspectionMapper;

    public InspectionResource(InspectionRepository inspectionRepository, InspectionMapper inspectionMapper) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionMapper = inspectionMapper;
    }

    /**
     * POST  /inspections : Create a new inspection.
     *
     * @param inspectionDTO the inspectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inspectionDTO, or with status 400 (Bad Request) if the inspection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inspections")
    @Timed
    public ResponseEntity<InspectionDTO> createInspection(@RequestBody InspectionDTO inspectionDTO) throws URISyntaxException {
        log.debug("REST request to save Inspection : {}", inspectionDTO);
        if (inspectionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inspection cannot already have an ID")).body(null);
        }
        Inspection inspection = inspectionMapper.toEntity(inspectionDTO);
        inspection = inspectionRepository.save(inspection);
        InspectionDTO result = inspectionMapper.toDto(inspection);
        return ResponseEntity.created(new URI("/api/inspections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inspections : Updates an existing inspection.
     *
     * @param inspectionDTO the inspectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inspectionDTO,
     * or with status 400 (Bad Request) if the inspectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the inspectionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inspections")
    @Timed
    public ResponseEntity<InspectionDTO> updateInspection(@RequestBody InspectionDTO inspectionDTO) throws URISyntaxException {
        log.debug("REST request to update Inspection : {}", inspectionDTO);
        if (inspectionDTO.getId() == null) {
            return createInspection(inspectionDTO);
        }
        Inspection inspection = inspectionMapper.toEntity(inspectionDTO);
        inspection = inspectionRepository.save(inspection);
        InspectionDTO result = inspectionMapper.toDto(inspection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inspectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inspections : get all the inspections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inspections in body
     */
    @GetMapping("/inspections")
    @Timed
    public List<InspectionDTO> getAllInspections() {
        log.debug("REST request to get all Inspections");
        List<Inspection> inspections = inspectionRepository.findAll();
        return inspectionMapper.toDto(inspections);
    }

    /**
     * GET  /inspections/:id : get the "id" inspection.
     *
     * @param id the id of the inspectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inspectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inspections/{id}")
    @Timed
    public ResponseEntity<InspectionDTO> getInspection(@PathVariable Long id) {
        log.debug("REST request to get Inspection : {}", id);
        Inspection inspection = inspectionRepository.findOne(id);
        InspectionDTO inspectionDTO = inspectionMapper.toDto(inspection);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inspectionDTO));
    }

    /**
     * DELETE  /inspections/:id : delete the "id" inspection.
     *
     * @param id the id of the inspectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inspections/{id}")
    @Timed
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        log.debug("REST request to delete Inspection : {}", id);
        inspectionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
