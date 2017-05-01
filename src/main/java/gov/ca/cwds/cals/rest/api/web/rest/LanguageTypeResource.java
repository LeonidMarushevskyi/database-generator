package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.LanguageType;

import gov.ca.cwds.cals.rest.api.repository.LanguageTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.LanguageTypeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.LanguageTypeMapper;
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
 * REST controller for managing LanguageType.
 */
@RestController
@RequestMapping("/api")
public class LanguageTypeResource {

    private final Logger log = LoggerFactory.getLogger(LanguageTypeResource.class);

    private static final String ENTITY_NAME = "languageType";
        
    private final LanguageTypeRepository languageTypeRepository;

    private final LanguageTypeMapper languageTypeMapper;

    public LanguageTypeResource(LanguageTypeRepository languageTypeRepository, LanguageTypeMapper languageTypeMapper) {
        this.languageTypeRepository = languageTypeRepository;
        this.languageTypeMapper = languageTypeMapper;
    }

    /**
     * POST  /language-types : Create a new languageType.
     *
     * @param languageTypeDTO the languageTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new languageTypeDTO, or with status 400 (Bad Request) if the languageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/language-types")
    @Timed
    public ResponseEntity<LanguageTypeDTO> createLanguageType(@Valid @RequestBody LanguageTypeDTO languageTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LanguageType : {}", languageTypeDTO);
        if (languageTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new languageType cannot already have an ID")).body(null);
        }
        LanguageType languageType = languageTypeMapper.languageTypeDTOToLanguageType(languageTypeDTO);
        languageType = languageTypeRepository.save(languageType);
        LanguageTypeDTO result = languageTypeMapper.languageTypeToLanguageTypeDTO(languageType);
        return ResponseEntity.created(new URI("/api/language-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /language-types : Updates an existing languageType.
     *
     * @param languageTypeDTO the languageTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated languageTypeDTO,
     * or with status 400 (Bad Request) if the languageTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the languageTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/language-types")
    @Timed
    public ResponseEntity<LanguageTypeDTO> updateLanguageType(@Valid @RequestBody LanguageTypeDTO languageTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LanguageType : {}", languageTypeDTO);
        if (languageTypeDTO.getId() == null) {
            return createLanguageType(languageTypeDTO);
        }
        LanguageType languageType = languageTypeMapper.languageTypeDTOToLanguageType(languageTypeDTO);
        languageType = languageTypeRepository.save(languageType);
        LanguageTypeDTO result = languageTypeMapper.languageTypeToLanguageTypeDTO(languageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, languageTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /language-types : get all the languageTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of languageTypes in body
     */
    @GetMapping("/language-types")
    @Timed
    public List<LanguageTypeDTO> getAllLanguageTypes() {
        log.debug("REST request to get all LanguageTypes");
        List<LanguageType> languageTypes = languageTypeRepository.findAll();
        return languageTypeMapper.languageTypesToLanguageTypeDTOs(languageTypes);
    }

    /**
     * GET  /language-types/:id : get the "id" languageType.
     *
     * @param id the id of the languageTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the languageTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/language-types/{id}")
    @Timed
    public ResponseEntity<LanguageTypeDTO> getLanguageType(@PathVariable Long id) {
        log.debug("REST request to get LanguageType : {}", id);
        LanguageType languageType = languageTypeRepository.findOne(id);
        LanguageTypeDTO languageTypeDTO = languageTypeMapper.languageTypeToLanguageTypeDTO(languageType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(languageTypeDTO));
    }

    /**
     * DELETE  /language-types/:id : delete the "id" languageType.
     *
     * @param id the id of the languageTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/language-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLanguageType(@PathVariable Long id) {
        log.debug("REST request to delete LanguageType : {}", id);
        languageTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
