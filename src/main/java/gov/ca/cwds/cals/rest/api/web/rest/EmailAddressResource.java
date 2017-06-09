package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.EmailAddress;

import gov.ca.cwds.cals.rest.api.repository.EmailAddressRepository;
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
 * REST controller for managing EmailAddress.
 */
@RestController
@RequestMapping("/api")
public class EmailAddressResource {

    private final Logger log = LoggerFactory.getLogger(EmailAddressResource.class);

    private static final String ENTITY_NAME = "emailAddress";
        
    private final EmailAddressRepository emailAddressRepository;

    public EmailAddressResource(EmailAddressRepository emailAddressRepository) {
        this.emailAddressRepository = emailAddressRepository;
    }

    /**
     * POST  /email-addresses : Create a new emailAddress.
     *
     * @param emailAddress the emailAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailAddress, or with status 400 (Bad Request) if the emailAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-addresses")
    @Timed
    public ResponseEntity<EmailAddress> createEmailAddress(@Valid @RequestBody EmailAddress emailAddress) throws URISyntaxException {
        log.debug("REST request to save EmailAddress : {}", emailAddress);
        if (emailAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new emailAddress cannot already have an ID")).body(null);
        }
        EmailAddress result = emailAddressRepository.save(emailAddress);
        return ResponseEntity.created(new URI("/api/email-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-addresses : Updates an existing emailAddress.
     *
     * @param emailAddress the emailAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailAddress,
     * or with status 400 (Bad Request) if the emailAddress is not valid,
     * or with status 500 (Internal Server Error) if the emailAddress couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-addresses")
    @Timed
    public ResponseEntity<EmailAddress> updateEmailAddress(@Valid @RequestBody EmailAddress emailAddress) throws URISyntaxException {
        log.debug("REST request to update EmailAddress : {}", emailAddress);
        if (emailAddress.getId() == null) {
            return createEmailAddress(emailAddress);
        }
        EmailAddress result = emailAddressRepository.save(emailAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-addresses : get all the emailAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emailAddresses in body
     */
    @GetMapping("/email-addresses")
    @Timed
    public List<EmailAddress> getAllEmailAddresses() {
        log.debug("REST request to get all EmailAddresses");
        List<EmailAddress> emailAddresses = emailAddressRepository.findAll();
        return emailAddresses;
    }

    /**
     * GET  /email-addresses/:id : get the "id" emailAddress.
     *
     * @param id the id of the emailAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailAddress, or with status 404 (Not Found)
     */
    @GetMapping("/email-addresses/{id}")
    @Timed
    public ResponseEntity<EmailAddress> getEmailAddress(@PathVariable Long id) {
        log.debug("REST request to get EmailAddress : {}", id);
        EmailAddress emailAddress = emailAddressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emailAddress));
    }

    /**
     * DELETE  /email-addresses/:id : delete the "id" emailAddress.
     *
     * @param id the id of the emailAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailAddress(@PathVariable Long id) {
        log.debug("REST request to delete EmailAddress : {}", id);
        emailAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
