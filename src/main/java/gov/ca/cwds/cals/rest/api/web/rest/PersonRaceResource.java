package gov.ca.cwds.cals.rest.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.ca.cwds.cals.rest.api.domain.PersonRace;

import gov.ca.cwds.cals.rest.api.repository.PersonRaceRepository;
import gov.ca.cwds.cals.rest.api.web.rest.util.HeaderUtil;
import gov.ca.cwds.cals.rest.api.service.dto.PersonRaceDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonRaceMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PersonRace.
 */
@RestController
@RequestMapping("/api")
public class PersonRaceResource {

    private final Logger log = LoggerFactory.getLogger(PersonRaceResource.class);

    private static final String ENTITY_NAME = "personRace";
        
    private final PersonRaceRepository personRaceRepository;

    private final PersonRaceMapper personRaceMapper;

    public PersonRaceResource(PersonRaceRepository personRaceRepository, PersonRaceMapper personRaceMapper) {
        this.personRaceRepository = personRaceRepository;
        this.personRaceMapper = personRaceMapper;
    }

    /**
     * POST  /person-races : Create a new personRace.
     *
     * @param personRaceDTO the personRaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personRaceDTO, or with status 400 (Bad Request) if the personRace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-races")
    @Timed
    public ResponseEntity<PersonRaceDTO> createPersonRace(@RequestBody PersonRaceDTO personRaceDTO) throws URISyntaxException {
        log.debug("REST request to save PersonRace : {}", personRaceDTO);
        if (personRaceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personRace cannot already have an ID")).body(null);
        }
        PersonRace personRace = personRaceMapper.personRaceDTOToPersonRace(personRaceDTO);
        personRace = personRaceRepository.save(personRace);
        PersonRaceDTO result = personRaceMapper.personRaceToPersonRaceDTO(personRace);
        return ResponseEntity.created(new URI("/api/person-races/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-races : Updates an existing personRace.
     *
     * @param personRaceDTO the personRaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personRaceDTO,
     * or with status 400 (Bad Request) if the personRaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the personRaceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-races")
    @Timed
    public ResponseEntity<PersonRaceDTO> updatePersonRace(@RequestBody PersonRaceDTO personRaceDTO) throws URISyntaxException {
        log.debug("REST request to update PersonRace : {}", personRaceDTO);
        if (personRaceDTO.getId() == null) {
            return createPersonRace(personRaceDTO);
        }
        PersonRace personRace = personRaceMapper.personRaceDTOToPersonRace(personRaceDTO);
        personRace = personRaceRepository.save(personRace);
        PersonRaceDTO result = personRaceMapper.personRaceToPersonRaceDTO(personRace);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personRaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-races : get all the personRaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personRaces in body
     */
    @GetMapping("/person-races")
    @Timed
    public List<PersonRaceDTO> getAllPersonRaces() {
        log.debug("REST request to get all PersonRaces");
        List<PersonRace> personRaces = personRaceRepository.findAll();
        return personRaceMapper.personRacesToPersonRaceDTOs(personRaces);
    }

    /**
     * GET  /person-races/:id : get the "id" personRace.
     *
     * @param id the id of the personRaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personRaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-races/{id}")
    @Timed
    public ResponseEntity<PersonRaceDTO> getPersonRace(@PathVariable Long id) {
        log.debug("REST request to get PersonRace : {}", id);
        PersonRace personRace = personRaceRepository.findOne(id);
        PersonRaceDTO personRaceDTO = personRaceMapper.personRaceToPersonRaceDTO(personRace);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personRaceDTO));
    }

    /**
     * DELETE  /person-races/:id : delete the "id" personRace.
     *
     * @param id the id of the personRaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-races/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonRace(@PathVariable Long id) {
        log.debug("REST request to delete PersonRace : {}", id);
        personRaceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
