package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonRace;
import gov.ca.cwds.cals.rest.api.repository.PersonRaceRepository;
import gov.ca.cwds.cals.rest.api.service.dto.PersonRaceDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonRaceMapper;
import gov.ca.cwds.cals.rest.api.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonRaceResource REST controller.
 *
 * @see PersonRaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonRaceResourceIntTest {

    @Autowired
    private PersonRaceRepository personRaceRepository;

    @Autowired
    private PersonRaceMapper personRaceMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonRaceMockMvc;

    private PersonRace personRace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonRaceResource personRaceResource = new PersonRaceResource(personRaceRepository, personRaceMapper);
        this.restPersonRaceMockMvc = MockMvcBuilders.standaloneSetup(personRaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonRace createEntity(EntityManager em) {
        PersonRace personRace = new PersonRace();
        return personRace;
    }

    @Before
    public void initTest() {
        personRace = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonRace() throws Exception {
        int databaseSizeBeforeCreate = personRaceRepository.findAll().size();

        // Create the PersonRace
        PersonRaceDTO personRaceDTO = personRaceMapper.toDto(personRace);
        restPersonRaceMockMvc.perform(post("/api/person-races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRaceDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonRace in the database
        List<PersonRace> personRaceList = personRaceRepository.findAll();
        assertThat(personRaceList).hasSize(databaseSizeBeforeCreate + 1);
        PersonRace testPersonRace = personRaceList.get(personRaceList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonRaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRaceRepository.findAll().size();

        // Create the PersonRace with an existing ID
        personRace.setId(1L);
        PersonRaceDTO personRaceDTO = personRaceMapper.toDto(personRace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonRaceMockMvc.perform(post("/api/person-races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonRace> personRaceList = personRaceRepository.findAll();
        assertThat(personRaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonRaces() throws Exception {
        // Initialize the database
        personRaceRepository.saveAndFlush(personRace);

        // Get all the personRaceList
        restPersonRaceMockMvc.perform(get("/api/person-races?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personRace.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonRace() throws Exception {
        // Initialize the database
        personRaceRepository.saveAndFlush(personRace);

        // Get the personRace
        restPersonRaceMockMvc.perform(get("/api/person-races/{id}", personRace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personRace.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonRace() throws Exception {
        // Get the personRace
        restPersonRaceMockMvc.perform(get("/api/person-races/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonRace() throws Exception {
        // Initialize the database
        personRaceRepository.saveAndFlush(personRace);
        int databaseSizeBeforeUpdate = personRaceRepository.findAll().size();

        // Update the personRace
        PersonRace updatedPersonRace = personRaceRepository.findOne(personRace.getId());
        PersonRaceDTO personRaceDTO = personRaceMapper.toDto(updatedPersonRace);

        restPersonRaceMockMvc.perform(put("/api/person-races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRaceDTO)))
            .andExpect(status().isOk());

        // Validate the PersonRace in the database
        List<PersonRace> personRaceList = personRaceRepository.findAll();
        assertThat(personRaceList).hasSize(databaseSizeBeforeUpdate);
        PersonRace testPersonRace = personRaceList.get(personRaceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonRace() throws Exception {
        int databaseSizeBeforeUpdate = personRaceRepository.findAll().size();

        // Create the PersonRace
        PersonRaceDTO personRaceDTO = personRaceMapper.toDto(personRace);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonRaceMockMvc.perform(put("/api/person-races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRaceDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonRace in the database
        List<PersonRace> personRaceList = personRaceRepository.findAll();
        assertThat(personRaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonRace() throws Exception {
        // Initialize the database
        personRaceRepository.saveAndFlush(personRace);
        int databaseSizeBeforeDelete = personRaceRepository.findAll().size();

        // Get the personRace
        restPersonRaceMockMvc.perform(delete("/api/person-races/{id}", personRace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonRace> personRaceList = personRaceRepository.findAll();
        assertThat(personRaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonRace.class);
        PersonRace personRace1 = new PersonRace();
        personRace1.setId(1L);
        PersonRace personRace2 = new PersonRace();
        personRace2.setId(personRace1.getId());
        assertThat(personRace1).isEqualTo(personRace2);
        personRace2.setId(2L);
        assertThat(personRace1).isNotEqualTo(personRace2);
        personRace1.setId(null);
        assertThat(personRace1).isNotEqualTo(personRace2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonRaceDTO.class);
        PersonRaceDTO personRaceDTO1 = new PersonRaceDTO();
        personRaceDTO1.setId(1L);
        PersonRaceDTO personRaceDTO2 = new PersonRaceDTO();
        assertThat(personRaceDTO1).isNotEqualTo(personRaceDTO2);
        personRaceDTO2.setId(personRaceDTO1.getId());
        assertThat(personRaceDTO1).isEqualTo(personRaceDTO2);
        personRaceDTO2.setId(2L);
        assertThat(personRaceDTO1).isNotEqualTo(personRaceDTO2);
        personRaceDTO1.setId(null);
        assertThat(personRaceDTO1).isNotEqualTo(personRaceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personRaceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personRaceMapper.fromId(null)).isNull();
    }
}
