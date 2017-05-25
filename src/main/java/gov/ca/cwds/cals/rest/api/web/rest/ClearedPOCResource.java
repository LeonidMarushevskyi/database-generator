package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.ClearedPOC;

import gov.ca.cwds.cals.rest.api.repository.ClearedPOCRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.ClearedPOCDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.ClearedPOCMapper;
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
 * REST controller for managing ClearedPOC.
 */
@RestController
@RequestMapping("/api")
public class ClearedPOCResource {

    private final Logger log = LoggerFactory.getLogger(ClearedPOCResource.class);

    private static final String ENTITY_NAME = "clearedPOC";
        
    private final ClearedPOCRepository clearedPOCRepository;

    private final ClearedPOCMapper clearedPOCMapper;

    public ClearedPOCResource(ClearedPOCRepository clearedPOCRepository, ClearedPOCMapper clearedPOCMapper) {
        this.clearedPOCRepository = clearedPOCRepository;
        this.clearedPOCMapper = clearedPOCMapper;
    }

    /**
     * POST  /cleared-pocs : Create a new clearedPOC.
     *
     * @param clearedPOCDTO the clearedPOCDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clearedPOCDTO, or with status 400 (Bad Request) if the clearedPOC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cleared-pocs")
    @Timed
    public ResponseEntity<ClearedPOCDTO> createClearedPOC(@RequestBody ClearedPOCDTO clearedPOCDTO) throws URISyntaxException {
        log.debug("REST request to save ClearedPOC : {}", clearedPOCDTO);
        if (clearedPOCDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clearedPOC cannot already have an ID")).body(null);
        }
        ClearedPOC clearedPOC = clearedPOCMapper.toEntity(clearedPOCDTO);
        clearedPOC = clearedPOCRepository.save(clearedPOC);
        ClearedPOCDTO result = clearedPOCMapper.toDto(clearedPOC);
        return ResponseEntity.created(new URI("/api/cleared-pocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cleared-pocs : Updates an existing clearedPOC.
     *
     * @param clearedPOCDTO the clearedPOCDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clearedPOCDTO,
     * or with status 400 (Bad Request) if the clearedPOCDTO is not valid,
     * or with status 500 (Internal Server Error) if the clearedPOCDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cleared-pocs")
    @Timed
    public ResponseEntity<ClearedPOCDTO> updateClearedPOC(@RequestBody ClearedPOCDTO clearedPOCDTO) throws URISyntaxException {
        log.debug("REST request to update ClearedPOC : {}", clearedPOCDTO);
        if (clearedPOCDTO.getId() == null) {
            return createClearedPOC(clearedPOCDTO);
        }
        ClearedPOC clearedPOC = clearedPOCMapper.toEntity(clearedPOCDTO);
        clearedPOC = clearedPOCRepository.save(clearedPOC);
        ClearedPOCDTO result = clearedPOCMapper.toDto(clearedPOC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clearedPOCDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cleared-pocs : get all the clearedPOCS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clearedPOCS in body
     */
    @GetMapping("/cleared-pocs")
    @Timed
    public List<ClearedPOCDTO> getAllClearedPOCS() {
        log.debug("REST request to get all ClearedPOCS");
        List<ClearedPOC> clearedPOCS = clearedPOCRepository.findAll();
        return clearedPOCMapper.toDto(clearedPOCS);
    }

    /**
     * GET  /cleared-pocs/:id : get the "id" clearedPOC.
     *
     * @param id the id of the clearedPOCDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clearedPOCDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cleared-pocs/{id}")
    @Timed
    public ResponseEntity<ClearedPOCDTO> getClearedPOC(@PathVariable Long id) {
        log.debug("REST request to get ClearedPOC : {}", id);
        ClearedPOC clearedPOC = clearedPOCRepository.findOne(id);
        ClearedPOCDTO clearedPOCDTO = clearedPOCMapper.toDto(clearedPOC);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clearedPOCDTO));
    }

    /**
     * DELETE  /cleared-pocs/:id : delete the "id" clearedPOC.
     *
     * @param id the id of the clearedPOCDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cleared-pocs/{id}")
    @Timed
    public ResponseEntity<Void> deleteClearedPOC(@PathVariable Long id) {
        log.debug("REST request to delete ClearedPOC : {}", id);
        clearedPOCRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
