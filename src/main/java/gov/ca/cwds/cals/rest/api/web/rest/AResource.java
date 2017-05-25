package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.A;

import gov.ca.cwds.cals.rest.api.repository.ARepository;
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
 * REST controller for managing A.
 */
@RestController
@RequestMapping("/api")
public class AResource {

    private final Logger log = LoggerFactory.getLogger(AResource.class);

    private static final String ENTITY_NAME = "a";
        
    private final ARepository aRepository;

    public AResource(ARepository aRepository) {
        this.aRepository = aRepository;
    }

    /**
     * POST  /a-s : Create a new a.
     *
     * @param a the a to create
     * @return the ResponseEntity with status 201 (Created) and with body the new a, or with status 400 (Bad Request) if the a has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/a-s")
    @Timed
    public ResponseEntity<A> createA(@Valid @RequestBody A a) throws URISyntaxException {
        log.debug("REST request to save A : {}", a);
        if (a.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new a cannot already have an ID")).body(null);
        }
        A result = aRepository.save(a);
        return ResponseEntity.created(new URI("/api/a-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /a-s : Updates an existing a.
     *
     * @param a the a to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated a,
     * or with status 400 (Bad Request) if the a is not valid,
     * or with status 500 (Internal Server Error) if the a couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/a-s")
    @Timed
    public ResponseEntity<A> updateA(@Valid @RequestBody A a) throws URISyntaxException {
        log.debug("REST request to update A : {}", a);
        if (a.getId() == null) {
            return createA(a);
        }
        A result = aRepository.save(a);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, a.getId().toString()))
            .body(result);
    }

    /**
     * GET  /a-s : get all the as.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of as in body
     */
    @GetMapping("/a-s")
    @Timed
    public List<A> getAllAS() {
        log.debug("REST request to get all AS");
        List<A> as = aRepository.findAll();
        return as;
    }

    /**
     * GET  /a-s/:id : get the "id" a.
     *
     * @param id the id of the a to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the a, or with status 404 (Not Found)
     */
    @GetMapping("/a-s/{id}")
    @Timed
    public ResponseEntity<A> getA(@PathVariable Long id) {
        log.debug("REST request to get A : {}", id);
        A a = aRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(a));
    }

    /**
     * DELETE  /a-s/:id : delete the "id" a.
     *
     * @param id the id of the a to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/a-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteA(@PathVariable Long id) {
        log.debug("REST request to delete A : {}", id);
        aRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
