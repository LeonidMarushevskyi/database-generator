package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.RaceType;
import gov.ca.cwds.cals.rest.api.repository.RaceTypeRepository;
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
 * Test class for the RaceTypeResource REST controller.
 *
 * @see RaceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class RaceTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RaceTypeRepository raceTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaceTypeMockMvc;

    private RaceType raceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaceTypeResource raceTypeResource = new RaceTypeResource(raceTypeRepository);
        this.restRaceTypeMockMvc = MockMvcBuilders.standaloneSetup(raceTypeResource)
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
    public static RaceType createEntity(EntityManager em) {
        RaceType raceType = new RaceType()
            .name(DEFAULT_NAME);
        return raceType;
    }

    @Before
    public void initTest() {
        raceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaceType() throws Exception {
        int databaseSizeBeforeCreate = raceTypeRepository.findAll().size();

        // Create the RaceType
        restRaceTypeMockMvc.perform(post("/api/race-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raceType)))
            .andExpect(status().isCreated());

        // Validate the RaceType in the database
        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RaceType testRaceType = raceTypeList.get(raceTypeList.size() - 1);
        assertThat(testRaceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRaceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raceTypeRepository.findAll().size();

        // Create the RaceType with an existing ID
        raceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaceTypeMockMvc.perform(post("/api/race-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raceType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = raceTypeRepository.findAll().size();
        // set the field null
        raceType.setName(null);

        // Create the RaceType, which fails.

        restRaceTypeMockMvc.perform(post("/api/race-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raceType)))
            .andExpect(status().isBadRequest());

        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRaceTypes() throws Exception {
        // Initialize the database
        raceTypeRepository.saveAndFlush(raceType);

        // Get all the raceTypeList
        restRaceTypeMockMvc.perform(get("/api/race-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRaceType() throws Exception {
        // Initialize the database
        raceTypeRepository.saveAndFlush(raceType);

        // Get the raceType
        restRaceTypeMockMvc.perform(get("/api/race-types/{id}", raceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaceType() throws Exception {
        // Get the raceType
        restRaceTypeMockMvc.perform(get("/api/race-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaceType() throws Exception {
        // Initialize the database
        raceTypeRepository.saveAndFlush(raceType);
        int databaseSizeBeforeUpdate = raceTypeRepository.findAll().size();

        // Update the raceType
        RaceType updatedRaceType = raceTypeRepository.findOne(raceType.getId());
        updatedRaceType
            .name(UPDATED_NAME);

        restRaceTypeMockMvc.perform(put("/api/race-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRaceType)))
            .andExpect(status().isOk());

        // Validate the RaceType in the database
        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeUpdate);
        RaceType testRaceType = raceTypeList.get(raceTypeList.size() - 1);
        assertThat(testRaceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRaceType() throws Exception {
        int databaseSizeBeforeUpdate = raceTypeRepository.findAll().size();

        // Create the RaceType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaceTypeMockMvc.perform(put("/api/race-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raceType)))
            .andExpect(status().isCreated());

        // Validate the RaceType in the database
        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaceType() throws Exception {
        // Initialize the database
        raceTypeRepository.saveAndFlush(raceType);
        int databaseSizeBeforeDelete = raceTypeRepository.findAll().size();

        // Get the raceType
        restRaceTypeMockMvc.perform(delete("/api/race-types/{id}", raceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RaceType> raceTypeList = raceTypeRepository.findAll();
        assertThat(raceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaceType.class);
        RaceType raceType1 = new RaceType();
        raceType1.setId(1L);
        RaceType raceType2 = new RaceType();
        raceType2.setId(raceType1.getId());
        assertThat(raceType1).isEqualTo(raceType2);
        raceType2.setId(2L);
        assertThat(raceType1).isNotEqualTo(raceType2);
        raceType1.setId(null);
        assertThat(raceType1).isNotEqualTo(raceType2);
    }
}
