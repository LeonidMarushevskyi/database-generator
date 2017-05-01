package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.RaceType;

import gov.ca.cwds.cals.rest.api.repository.RaceTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.RaceTypeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.RaceTypeMapper;
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
 * REST controller for managing RaceType.
 */
@RestController
@RequestMapping("/api")
public class RaceTypeResource {

    private final Logger log = LoggerFactory.getLogger(RaceTypeResource.class);

    private static final String ENTITY_NAME = "raceType";
        
    private final RaceTypeRepository raceTypeRepository;

    private final RaceTypeMapper raceTypeMapper;

    public RaceTypeResource(RaceTypeRepository raceTypeRepository, RaceTypeMapper raceTypeMapper) {
        this.raceTypeRepository = raceTypeRepository;
        this.raceTypeMapper = raceTypeMapper;
    }

    /**
     * POST  /race-types : Create a new raceType.
     *
     * @param raceTypeDTO the raceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raceTypeDTO, or with status 400 (Bad Request) if the raceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/race-types")
    @Timed
    public ResponseEntity<RaceTypeDTO> createRaceType(@Valid @RequestBody RaceTypeDTO raceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RaceType : {}", raceTypeDTO);
        if (raceTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new raceType cannot already have an ID")).body(null);
        }
        RaceType raceType = raceTypeMapper.raceTypeDTOToRaceType(raceTypeDTO);
        raceType = raceTypeRepository.save(raceType);
        RaceTypeDTO result = raceTypeMapper.raceTypeToRaceTypeDTO(raceType);
        return ResponseEntity.created(new URI("/api/race-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /race-types : Updates an existing raceType.
     *
     * @param raceTypeDTO the raceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raceTypeDTO,
     * or with status 400 (Bad Request) if the raceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the raceTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/race-types")
    @Timed
    public ResponseEntity<RaceTypeDTO> updateRaceType(@Valid @RequestBody RaceTypeDTO raceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update RaceType : {}", raceTypeDTO);
        if (raceTypeDTO.getId() == null) {
            return createRaceType(raceTypeDTO);
        }
        RaceType raceType = raceTypeMapper.raceTypeDTOToRaceType(raceTypeDTO);
        raceType = raceTypeRepository.save(raceType);
        RaceTypeDTO result = raceTypeMapper.raceTypeToRaceTypeDTO(raceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /race-types : get all the raceTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of raceTypes in body
     */
    @GetMapping("/race-types")
    @Timed
    public List<RaceTypeDTO> getAllRaceTypes() {
        log.debug("REST request to get all RaceTypes");
        List<RaceType> raceTypes = raceTypeRepository.findAll();
        return raceTypeMapper.raceTypesToRaceTypeDTOs(raceTypes);
    }

    /**
     * GET  /race-types/:id : get the "id" raceType.
     *
     * @param id the id of the raceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/race-types/{id}")
    @Timed
    public ResponseEntity<RaceTypeDTO> getRaceType(@PathVariable Long id) {
        log.debug("REST request to get RaceType : {}", id);
        RaceType raceType = raceTypeRepository.findOne(id);
        RaceTypeDTO raceTypeDTO = raceTypeMapper.raceTypeToRaceTypeDTO(raceType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raceTypeDTO));
    }

    /**
     * DELETE  /race-types/:id : delete the "id" raceType.
     *
     * @param id the id of the raceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/race-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaceType(@PathVariable Long id) {
        log.debug("REST request to delete RaceType : {}", id);
        raceTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
