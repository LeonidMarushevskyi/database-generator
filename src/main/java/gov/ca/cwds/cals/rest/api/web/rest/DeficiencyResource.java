package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.Deficiency;

import gov.ca.cwds.cals.rest.api.repository.DeficiencyRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.DeficiencyDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.DeficiencyMapper;
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
 * REST controller for managing Deficiency.
 */
@RestController
@RequestMapping("/api")
public class DeficiencyResource {

    private final Logger log = LoggerFactory.getLogger(DeficiencyResource.class);

    private static final String ENTITY_NAME = "deficiency";
        
    private final DeficiencyRepository deficiencyRepository;

    private final DeficiencyMapper deficiencyMapper;

    public DeficiencyResource(DeficiencyRepository deficiencyRepository, DeficiencyMapper deficiencyMapper) {
        this.deficiencyRepository = deficiencyRepository;
        this.deficiencyMapper = deficiencyMapper;
    }

    /**
     * POST  /deficiencies : Create a new deficiency.
     *
     * @param deficiencyDTO the deficiencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deficiencyDTO, or with status 400 (Bad Request) if the deficiency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deficiencies")
    @Timed
    public ResponseEntity<DeficiencyDTO> createDeficiency(@RequestBody DeficiencyDTO deficiencyDTO) throws URISyntaxException {
        log.debug("REST request to save Deficiency : {}", deficiencyDTO);
        if (deficiencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deficiency cannot already have an ID")).body(null);
        }
        Deficiency deficiency = deficiencyMapper.toEntity(deficiencyDTO);
        deficiency = deficiencyRepository.save(deficiency);
        DeficiencyDTO result = deficiencyMapper.toDto(deficiency);
        return ResponseEntity.created(new URI("/api/deficiencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deficiencies : Updates an existing deficiency.
     *
     * @param deficiencyDTO the deficiencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deficiencyDTO,
     * or with status 400 (Bad Request) if the deficiencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the deficiencyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deficiencies")
    @Timed
    public ResponseEntity<DeficiencyDTO> updateDeficiency(@RequestBody DeficiencyDTO deficiencyDTO) throws URISyntaxException {
        log.debug("REST request to update Deficiency : {}", deficiencyDTO);
        if (deficiencyDTO.getId() == null) {
            return createDeficiency(deficiencyDTO);
        }
        Deficiency deficiency = deficiencyMapper.toEntity(deficiencyDTO);
        deficiency = deficiencyRepository.save(deficiency);
        DeficiencyDTO result = deficiencyMapper.toDto(deficiency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deficiencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deficiencies : get all the deficiencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deficiencies in body
     */
    @GetMapping("/deficiencies")
    @Timed
    public List<DeficiencyDTO> getAllDeficiencies() {
        log.debug("REST request to get all Deficiencies");
        List<Deficiency> deficiencies = deficiencyRepository.findAll();
        return deficiencyMapper.toDto(deficiencies);
    }

    /**
     * GET  /deficiencies/:id : get the "id" deficiency.
     *
     * @param id the id of the deficiencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deficiencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deficiencies/{id}")
    @Timed
    public ResponseEntity<DeficiencyDTO> getDeficiency(@PathVariable Long id) {
        log.debug("REST request to get Deficiency : {}", id);
        Deficiency deficiency = deficiencyRepository.findOne(id);
        DeficiencyDTO deficiencyDTO = deficiencyMapper.toDto(deficiency);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deficiencyDTO));
    }

    /**
     * DELETE  /deficiencies/:id : delete the "id" deficiency.
     *
     * @param id the id of the deficiencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deficiencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeficiency(@PathVariable Long id) {
        log.debug("REST request to delete Deficiency : {}", id);
        deficiencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
