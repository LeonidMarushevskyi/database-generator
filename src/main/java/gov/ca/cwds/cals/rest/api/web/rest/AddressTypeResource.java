package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.AddressType;

import gov.ca.cwds.cals.rest.api.repository.AddressTypeRepository;
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
 * REST controller for managing AddressType.
 */
@RestController
@RequestMapping("/api")
public class AddressTypeResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypeResource.class);

    private static final String ENTITY_NAME = "addressType";
        
    private final AddressTypeRepository addressTypeRepository;

    public AddressTypeResource(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    /**
     * POST  /address-types : Create a new addressType.
     *
     * @param addressType the addressType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressType, or with status 400 (Bad Request) if the addressType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-types")
    @Timed
    public ResponseEntity<AddressType> createAddressType(@Valid @RequestBody AddressType addressType) throws URISyntaxException {
        log.debug("REST request to save AddressType : {}", addressType);
        if (addressType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new addressType cannot already have an ID")).body(null);
        }
        AddressType result = addressTypeRepository.save(addressType);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-types : Updates an existing addressType.
     *
     * @param addressType the addressType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressType,
     * or with status 400 (Bad Request) if the addressType is not valid,
     * or with status 500 (Internal Server Error) if the addressType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-types")
    @Timed
    public ResponseEntity<AddressType> updateAddressType(@Valid @RequestBody AddressType addressType) throws URISyntaxException {
        log.debug("REST request to update AddressType : {}", addressType);
        if (addressType.getId() == null) {
            return createAddressType(addressType);
        }
        AddressType result = addressTypeRepository.save(addressType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-types : get all the addressTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addressTypes in body
     */
    @GetMapping("/address-types")
    @Timed
    public List<AddressType> getAllAddressTypes() {
        log.debug("REST request to get all AddressTypes");
        List<AddressType> addressTypes = addressTypeRepository.findAll();
        return addressTypes;
    }

    /**
     * GET  /address-types/:id : get the "id" addressType.
     *
     * @param id the id of the addressType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressType, or with status 404 (Not Found)
     */
    @GetMapping("/address-types/{id}")
    @Timed
    public ResponseEntity<AddressType> getAddressType(@PathVariable Long id) {
        log.debug("REST request to get AddressType : {}", id);
        AddressType addressType = addressTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addressType));
    }

    /**
     * DELETE  /address-types/:id : delete the "id" addressType.
     *
     * @param id the id of the addressType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressType(@PathVariable Long id) {
        log.debug("REST request to delete AddressType : {}", id);
        addressTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
