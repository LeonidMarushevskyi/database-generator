package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.DistrictOffice;

import gov.ca.cwds.cals.rest.api.repository.DistrictOfficeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.DistrictOfficeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.DistrictOfficeMapper;
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
 * REST controller for managing DistrictOffice.
 */
@RestController
@RequestMapping("/api")
public class DistrictOfficeResource {

    private final Logger log = LoggerFactory.getLogger(DistrictOfficeResource.class);

    private static final String ENTITY_NAME = "districtOffice";
        
    private final DistrictOfficeRepository districtOfficeRepository;

    private final DistrictOfficeMapper districtOfficeMapper;

    public DistrictOfficeResource(DistrictOfficeRepository districtOfficeRepository, DistrictOfficeMapper districtOfficeMapper) {
        this.districtOfficeRepository = districtOfficeRepository;
        this.districtOfficeMapper = districtOfficeMapper;
    }

    /**
     * POST  /district-offices : Create a new districtOffice.
     *
     * @param districtOfficeDTO the districtOfficeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new districtOfficeDTO, or with status 400 (Bad Request) if the districtOffice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/district-offices")
    @Timed
    public ResponseEntity<DistrictOfficeDTO> createDistrictOffice(@Valid @RequestBody DistrictOfficeDTO districtOfficeDTO) throws URISyntaxException {
        log.debug("REST request to save DistrictOffice : {}", districtOfficeDTO);
        if (districtOfficeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new districtOffice cannot already have an ID")).body(null);
        }
        DistrictOffice districtOffice = districtOfficeMapper.districtOfficeDTOToDistrictOffice(districtOfficeDTO);
        districtOffice = districtOfficeRepository.save(districtOffice);
        DistrictOfficeDTO result = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);
        return ResponseEntity.created(new URI("/api/district-offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /district-offices : Updates an existing districtOffice.
     *
     * @param districtOfficeDTO the districtOfficeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated districtOfficeDTO,
     * or with status 400 (Bad Request) if the districtOfficeDTO is not valid,
     * or with status 500 (Internal Server Error) if the districtOfficeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/district-offices")
    @Timed
    public ResponseEntity<DistrictOfficeDTO> updateDistrictOffice(@Valid @RequestBody DistrictOfficeDTO districtOfficeDTO) throws URISyntaxException {
        log.debug("REST request to update DistrictOffice : {}", districtOfficeDTO);
        if (districtOfficeDTO.getId() == null) {
            return createDistrictOffice(districtOfficeDTO);
        }
        DistrictOffice districtOffice = districtOfficeMapper.districtOfficeDTOToDistrictOffice(districtOfficeDTO);
        districtOffice = districtOfficeRepository.save(districtOffice);
        DistrictOfficeDTO result = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, districtOfficeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /district-offices : get all the districtOffices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of districtOffices in body
     */
    @GetMapping("/district-offices")
    @Timed
    public List<DistrictOfficeDTO> getAllDistrictOffices() {
        log.debug("REST request to get all DistrictOffices");
        List<DistrictOffice> districtOffices = districtOfficeRepository.findAll();
        return districtOfficeMapper.districtOfficesToDistrictOfficeDTOs(districtOffices);
    }

    /**
     * GET  /district-offices/:id : get the "id" districtOffice.
     *
     * @param id the id of the districtOfficeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the districtOfficeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/district-offices/{id}")
    @Timed
    public ResponseEntity<DistrictOfficeDTO> getDistrictOffice(@PathVariable Long id) {
        log.debug("REST request to get DistrictOffice : {}", id);
        DistrictOffice districtOffice = districtOfficeRepository.findOne(id);
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(districtOfficeDTO));
    }

    /**
     * DELETE  /district-offices/:id : delete the "id" districtOffice.
     *
     * @param id the id of the districtOfficeDTO to delete
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
