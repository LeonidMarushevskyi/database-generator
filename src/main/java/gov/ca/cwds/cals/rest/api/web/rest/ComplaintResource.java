package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Complaint;

import gov.ca.cwds.cals.rest.api.repository.ComplaintRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.ComplaintDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.ComplaintMapper;
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
 * REST controller for managing Complaint.
 */
@RestController
@RequestMapping("/api")
public class ComplaintResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintResource.class);

    private static final String ENTITY_NAME = "complaint";
        
    private final ComplaintRepository complaintRepository;

    private final ComplaintMapper complaintMapper;

    public ComplaintResource(ComplaintRepository complaintRepository, ComplaintMapper complaintMapper) {
        this.complaintRepository = complaintRepository;
        this.complaintMapper = complaintMapper;
    }

    /**
     * POST  /complaints : Create a new complaint.
     *
     * @param complaintDTO the complaintDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new complaintDTO, or with status 400 (Bad Request) if the complaint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/complaints")
    @Timed
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) throws URISyntaxException {
        log.debug("REST request to save Complaint : {}", complaintDTO);
        if (complaintDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new complaint cannot already have an ID")).body(null);
        }
        Complaint complaint = complaintMapper.toEntity(complaintDTO);
        complaint = complaintRepository.save(complaint);
        ComplaintDTO result = complaintMapper.toDto(complaint);
        return ResponseEntity.created(new URI("/api/complaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /complaints : Updates an existing complaint.
     *
     * @param complaintDTO the complaintDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated complaintDTO,
     * or with status 400 (Bad Request) if the complaintDTO is not valid,
     * or with status 500 (Internal Server Error) if the complaintDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/complaints")
    @Timed
    public ResponseEntity<ComplaintDTO> updateComplaint(@RequestBody ComplaintDTO complaintDTO) throws URISyntaxException {
        log.debug("REST request to update Complaint : {}", complaintDTO);
        if (complaintDTO.getId() == null) {
            return createComplaint(complaintDTO);
        }
        Complaint complaint = complaintMapper.toEntity(complaintDTO);
        complaint = complaintRepository.save(complaint);
        ComplaintDTO result = complaintMapper.toDto(complaint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, complaintDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /complaints : get all the complaints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of complaints in body
     */
    @GetMapping("/complaints")
    @Timed
    public List<ComplaintDTO> getAllComplaints() {
        log.debug("REST request to get all Complaints");
        List<Complaint> complaints = complaintRepository.findAll();
        return complaintMapper.toDto(complaints);
    }

    /**
     * GET  /complaints/:id : get the "id" complaint.
     *
     * @param id the id of the complaintDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the complaintDTO, or with status 404 (Not Found)
     */
    @GetMapping("/complaints/{id}")
    @Timed
    public ResponseEntity<ComplaintDTO> getComplaint(@PathVariable Long id) {
        log.debug("REST request to get Complaint : {}", id);
        Complaint complaint = complaintRepository.findOne(id);
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(complaintDTO));
    }

    /**
     * DELETE  /complaints/:id : delete the "id" complaint.
     *
     * @param id the id of the complaintDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/complaints/{id}")
    @Timed
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        log.debug("REST request to delete Complaint : {}", id);
        complaintRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
