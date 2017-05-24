package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.County;

import gov.ca.cwds.cals.rest.api.repository.CountyRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.CountyDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.CountyMapper;
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
 * REST controller for managing County.
 */
@RestController
@RequestMapping("/api")
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);

    private static final String ENTITY_NAME = "county";
        
    private final CountyRepository countyRepository;

    private final CountyMapper countyMapper;

    public CountyResource(CountyRepository countyRepository, CountyMapper countyMapper) {
        this.countyRepository = countyRepository;
        this.countyMapper = countyMapper;
    }

    /**
     * POST  /counties : Create a new county.
     *
     * @param countyDTO the countyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countyDTO, or with status 400 (Bad Request) if the county has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/counties")
    @Timed
    public ResponseEntity<CountyDTO> createCounty(@Valid @RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to save County : {}", countyDTO);
        if (countyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new county cannot already have an ID")).body(null);
        }
        County county = countyMapper.countyDTOToCounty(countyDTO);
        county = countyRepository.save(county);
        CountyDTO result = countyMapper.countyToCountyDTO(county);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /counties : Updates an existing county.
     *
     * @param countyDTO the countyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countyDTO,
     * or with status 400 (Bad Request) if the countyDTO is not valid,
     * or with status 500 (Internal Server Error) if the countyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/counties")
    @Timed
    public ResponseEntity<CountyDTO> updateCounty(@Valid @RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to update County : {}", countyDTO);
        if (countyDTO.getId() == null) {
            return createCounty(countyDTO);
        }
        County county = countyMapper.countyDTOToCounty(countyDTO);
        county = countyRepository.save(county);
        CountyDTO result = countyMapper.countyToCountyDTO(county);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /counties : get all the counties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of counties in body
     */
    @GetMapping("/counties")
    @Timed
    public List<CountyDTO> getAllCounties() {
        log.debug("REST request to get all Counties");
        List<County> counties = countyRepository.findAll();
        return countyMapper.countiesToCountyDTOs(counties);
    }

    /**
     * GET  /counties/:id : get the "id" county.
     *
     * @param id the id of the countyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/counties/{id}")
    @Timed
    public ResponseEntity<CountyDTO> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        County county = countyRepository.findOne(id);
        CountyDTO countyDTO = countyMapper.countyToCountyDTO(county);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countyDTO));
    }

    /**
     * DELETE  /counties/:id : delete the "id" county.
     *
     * @param id the id of the countyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/counties/{id}")
    @Timed
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
