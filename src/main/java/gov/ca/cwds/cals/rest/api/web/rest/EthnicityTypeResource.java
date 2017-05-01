package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.EthnicityType;

import gov.ca.cwds.cals.rest.api.repository.EthnicityTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.EthnicityTypeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.EthnicityTypeMapper;
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
 * REST controller for managing EthnicityType.
 */
@RestController
@RequestMapping("/api")
public class EthnicityTypeResource {

    private final Logger log = LoggerFactory.getLogger(EthnicityTypeResource.class);

    private static final String ENTITY_NAME = "ethnicityType";
        
    private final EthnicityTypeRepository ethnicityTypeRepository;

    private final EthnicityTypeMapper ethnicityTypeMapper;

    public EthnicityTypeResource(EthnicityTypeRepository ethnicityTypeRepository, EthnicityTypeMapper ethnicityTypeMapper) {
        this.ethnicityTypeRepository = ethnicityTypeRepository;
        this.ethnicityTypeMapper = ethnicityTypeMapper;
    }

    /**
     * POST  /ethnicity-types : Create a new ethnicityType.
     *
     * @param ethnicityTypeDTO the ethnicityTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ethnicityTypeDTO, or with status 400 (Bad Request) if the ethnicityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ethnicity-types")
    @Timed
    public ResponseEntity<EthnicityTypeDTO> createEthnicityType(@Valid @RequestBody EthnicityTypeDTO ethnicityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EthnicityType : {}", ethnicityTypeDTO);
        if (ethnicityTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ethnicityType cannot already have an ID")).body(null);
        }
        EthnicityType ethnicityType = ethnicityTypeMapper.ethnicityTypeDTOToEthnicityType(ethnicityTypeDTO);
        ethnicityType = ethnicityTypeRepository.save(ethnicityType);
        EthnicityTypeDTO result = ethnicityTypeMapper.ethnicityTypeToEthnicityTypeDTO(ethnicityType);
        return ResponseEntity.created(new URI("/api/ethnicity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ethnicity-types : Updates an existing ethnicityType.
     *
     * @param ethnicityTypeDTO the ethnicityTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ethnicityTypeDTO,
     * or with status 400 (Bad Request) if the ethnicityTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the ethnicityTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ethnicity-types")
    @Timed
    public ResponseEntity<EthnicityTypeDTO> updateEthnicityType(@Valid @RequestBody EthnicityTypeDTO ethnicityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EthnicityType : {}", ethnicityTypeDTO);
        if (ethnicityTypeDTO.getId() == null) {
            return createEthnicityType(ethnicityTypeDTO);
        }
        EthnicityType ethnicityType = ethnicityTypeMapper.ethnicityTypeDTOToEthnicityType(ethnicityTypeDTO);
        ethnicityType = ethnicityTypeRepository.save(ethnicityType);
        EthnicityTypeDTO result = ethnicityTypeMapper.ethnicityTypeToEthnicityTypeDTO(ethnicityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ethnicityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ethnicity-types : get all the ethnicityTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ethnicityTypes in body
     */
    @GetMapping("/ethnicity-types")
    @Timed
    public List<EthnicityTypeDTO> getAllEthnicityTypes() {
        log.debug("REST request to get all EthnicityTypes");
        List<EthnicityType> ethnicityTypes = ethnicityTypeRepository.findAll();
        return ethnicityTypeMapper.ethnicityTypesToEthnicityTypeDTOs(ethnicityTypes);
    }

    /**
     * GET  /ethnicity-types/:id : get the "id" ethnicityType.
     *
     * @param id the id of the ethnicityTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ethnicityTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ethnicity-types/{id}")
    @Timed
    public ResponseEntity<EthnicityTypeDTO> getEthnicityType(@PathVariable Long id) {
        log.debug("REST request to get EthnicityType : {}", id);
        EthnicityType ethnicityType = ethnicityTypeRepository.findOne(id);
        EthnicityTypeDTO ethnicityTypeDTO = ethnicityTypeMapper.ethnicityTypeToEthnicityTypeDTO(ethnicityType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ethnicityTypeDTO));
    }

    /**
     * DELETE  /ethnicity-types/:id : delete the "id" ethnicityType.
     *
     * @param id the id of the ethnicityTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ethnicity-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEthnicityType(@PathVariable Long id) {
        log.debug("REST request to delete EthnicityType : {}", id);
        ethnicityTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
