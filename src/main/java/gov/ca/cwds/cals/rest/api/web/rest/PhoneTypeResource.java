package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PhoneType;

import gov.ca.cwds.cals.rest.api.repository.PhoneTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PhoneTypeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PhoneTypeMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PhoneType.
 */
@RestController
@RequestMapping("/api")
public class PhoneTypeResource {

    private final Logger log = LoggerFactory.getLogger(PhoneTypeResource.class);

    private static final String ENTITY_NAME = "phoneType";
        
    private final PhoneTypeRepository phoneTypeRepository;

    private final PhoneTypeMapper phoneTypeMapper;

    public PhoneTypeResource(PhoneTypeRepository phoneTypeRepository, PhoneTypeMapper phoneTypeMapper) {
        this.phoneTypeRepository = phoneTypeRepository;
        this.phoneTypeMapper = phoneTypeMapper;
    }

    /**
     * POST  /phone-types : Create a new phoneType.
     *
     * @param phoneTypeDTO the phoneTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phoneTypeDTO, or with status 400 (Bad Request) if the phoneType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phone-types")
    @Timed
    public ResponseEntity<PhoneTypeDTO> createPhoneType(@Valid @RequestBody PhoneTypeDTO phoneTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PhoneType : {}", phoneTypeDTO);
        if (phoneTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new phoneType cannot already have an ID")).body(null);
        }
        PhoneType phoneType = phoneTypeMapper.toEntity(phoneTypeDTO);
        phoneType = phoneTypeRepository.save(phoneType);
        PhoneTypeDTO result = phoneTypeMapper.toDto(phoneType);
        return ResponseEntity.created(new URI("/api/phone-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phone-types : Updates an existing phoneType.
     *
     * @param phoneTypeDTO the phoneTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phoneTypeDTO,
     * or with status 400 (Bad Request) if the phoneTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the phoneTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phone-types")
    @Timed
    public ResponseEntity<PhoneTypeDTO> updatePhoneType(@Valid @RequestBody PhoneTypeDTO phoneTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PhoneType : {}", phoneTypeDTO);
        if (phoneTypeDTO.getId() == null) {
            return createPhoneType(phoneTypeDTO);
        }
        PhoneType phoneType = phoneTypeMapper.toEntity(phoneTypeDTO);
        phoneType = phoneTypeRepository.save(phoneType);
        PhoneTypeDTO result = phoneTypeMapper.toDto(phoneType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phoneTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phone-types : get all the phoneTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of phoneTypes in body
     */
    @GetMapping("/phone-types")
    @Timed
    public List<PhoneTypeDTO> getAllPhoneTypes() {
        log.debug("REST request to get all PhoneTypes");
        List<PhoneType> phoneTypes = phoneTypeRepository.findAll();
        return phoneTypeMapper.toDto(phoneTypes);
    }

    /**
     * GET  /phone-types/:id : get the "id" phoneType.
     *
     * @param id the id of the phoneTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phoneTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/phone-types/{id}")
    @Timed
    public ResponseEntity<PhoneTypeDTO> getPhoneType(@PathVariable Long id) {
        log.debug("REST request to get PhoneType : {}", id);
        PhoneType phoneType = phoneTypeRepository.findOne(id);
        PhoneTypeDTO phoneTypeDTO = phoneTypeMapper.toDto(phoneType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(phoneTypeDTO));
    }

    /**
     * DELETE  /phone-types/:id : delete the "id" phoneType.
     *
     * @param id the id of the phoneTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phone-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoneType(@PathVariable Long id) {
        log.debug("REST request to delete PhoneType : {}", id);
        phoneTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
