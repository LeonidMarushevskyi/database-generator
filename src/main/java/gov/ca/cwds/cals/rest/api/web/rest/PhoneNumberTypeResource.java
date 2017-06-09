package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PhoneNumberType;

import gov.ca.cwds.cals.rest.api.repository.PhoneNumberTypeRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
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
 * REST controller for managing PhoneNumberType.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberTypeResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeResource.class);

    private static final String ENTITY_NAME = "phoneNumberType";
        
    private final PhoneNumberTypeRepository phoneNumberTypeRepository;

    public PhoneNumberTypeResource(PhoneNumberTypeRepository phoneNumberTypeRepository) {
        this.phoneNumberTypeRepository = phoneNumberTypeRepository;
    }

    /**
     * POST  /phone-number-types : Create a new phoneNumberType.
     *
     * @param phoneNumberType the phoneNumberType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phoneNumberType, or with status 400 (Bad Request) if the phoneNumberType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phone-number-types")
    @Timed
    public ResponseEntity<PhoneNumberType> createPhoneNumberType(@Valid @RequestBody PhoneNumberType phoneNumberType) throws URISyntaxException {
        log.debug("REST request to save PhoneNumberType : {}", phoneNumberType);
        if (phoneNumberType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new phoneNumberType cannot already have an ID")).body(null);
        }
        PhoneNumberType result = phoneNumberTypeRepository.save(phoneNumberType);
        return ResponseEntity.created(new URI("/api/phone-number-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phone-number-types : Updates an existing phoneNumberType.
     *
     * @param phoneNumberType the phoneNumberType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phoneNumberType,
     * or with status 400 (Bad Request) if the phoneNumberType is not valid,
     * or with status 500 (Internal Server Error) if the phoneNumberType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phone-number-types")
    @Timed
    public ResponseEntity<PhoneNumberType> updatePhoneNumberType(@Valid @RequestBody PhoneNumberType phoneNumberType) throws URISyntaxException {
        log.debug("REST request to update PhoneNumberType : {}", phoneNumberType);
        if (phoneNumberType.getId() == null) {
            return createPhoneNumberType(phoneNumberType);
        }
        PhoneNumberType result = phoneNumberTypeRepository.save(phoneNumberType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phoneNumberType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phone-number-types : get all the phoneNumberTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of phoneNumberTypes in body
     */
    @GetMapping("/phone-number-types")
    @Timed
    public List<PhoneNumberType> getAllPhoneNumberTypes() {
        log.debug("REST request to get all PhoneNumberTypes");
        List<PhoneNumberType> phoneNumberTypes = phoneNumberTypeRepository.findAll();
        return phoneNumberTypes;
    }

    /**
     * GET  /phone-number-types/:id : get the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phoneNumberType, or with status 404 (Not Found)
     */
    @GetMapping("/phone-number-types/{id}")
    @Timed
    public ResponseEntity<PhoneNumberType> getPhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumberType : {}", id);
        PhoneNumberType phoneNumberType = phoneNumberTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(phoneNumberType));
    }

    /**
     * DELETE  /phone-number-types/:id : delete the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phone-number-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumberType : {}", id);
        phoneNumberTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
