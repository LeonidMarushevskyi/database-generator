package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.EthnicityType;
import gov.ca.cwds.cals.rest.api.repository.EthnicityTypeRepository;
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
 * Test class for the EthnicityTypeResource REST controller.
 *
 * @see EthnicityTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EthnicityTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EthnicityTypeRepository ethnicityTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEthnicityTypeMockMvc;

    private EthnicityType ethnicityType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EthnicityTypeResource ethnicityTypeResource = new EthnicityTypeResource(ethnicityTypeRepository);
        this.restEthnicityTypeMockMvc = MockMvcBuilders.standaloneSetup(ethnicityTypeResource)
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
    public static EthnicityType createEntity(EntityManager em) {
        EthnicityType ethnicityType = new EthnicityType()
            .name(DEFAULT_NAME);
        return ethnicityType;
    }

    @Before
    public void initTest() {
        ethnicityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEthnicityType() throws Exception {
        int databaseSizeBeforeCreate = ethnicityTypeRepository.findAll().size();

        // Create the EthnicityType
        restEthnicityTypeMockMvc.perform(post("/api/ethnicity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicityType)))
            .andExpect(status().isCreated());

        // Validate the EthnicityType in the database
        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EthnicityType testEthnicityType = ethnicityTypeList.get(ethnicityTypeList.size() - 1);
        assertThat(testEthnicityType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEthnicityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ethnicityTypeRepository.findAll().size();

        // Create the EthnicityType with an existing ID
        ethnicityType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEthnicityTypeMockMvc.perform(post("/api/ethnicity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicityType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ethnicityTypeRepository.findAll().size();
        // set the field null
        ethnicityType.setName(null);

        // Create the EthnicityType, which fails.

        restEthnicityTypeMockMvc.perform(post("/api/ethnicity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicityType)))
            .andExpect(status().isBadRequest());

        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEthnicityTypes() throws Exception {
        // Initialize the database
        ethnicityTypeRepository.saveAndFlush(ethnicityType);

        // Get all the ethnicityTypeList
        restEthnicityTypeMockMvc.perform(get("/api/ethnicity-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethnicityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEthnicityType() throws Exception {
        // Initialize the database
        ethnicityTypeRepository.saveAndFlush(ethnicityType);

        // Get the ethnicityType
        restEthnicityTypeMockMvc.perform(get("/api/ethnicity-types/{id}", ethnicityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ethnicityType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEthnicityType() throws Exception {
        // Get the ethnicityType
        restEthnicityTypeMockMvc.perform(get("/api/ethnicity-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEthnicityType() throws Exception {
        // Initialize the database
        ethnicityTypeRepository.saveAndFlush(ethnicityType);
        int databaseSizeBeforeUpdate = ethnicityTypeRepository.findAll().size();

        // Update the ethnicityType
        EthnicityType updatedEthnicityType = ethnicityTypeRepository.findOne(ethnicityType.getId());
        updatedEthnicityType
            .name(UPDATED_NAME);

        restEthnicityTypeMockMvc.perform(put("/api/ethnicity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEthnicityType)))
            .andExpect(status().isOk());

        // Validate the EthnicityType in the database
        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeUpdate);
        EthnicityType testEthnicityType = ethnicityTypeList.get(ethnicityTypeList.size() - 1);
        assertThat(testEthnicityType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEthnicityType() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityTypeRepository.findAll().size();

        // Create the EthnicityType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEthnicityTypeMockMvc.perform(put("/api/ethnicity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicityType)))
            .andExpect(status().isCreated());

        // Validate the EthnicityType in the database
        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEthnicityType() throws Exception {
        // Initialize the database
        ethnicityTypeRepository.saveAndFlush(ethnicityType);
        int databaseSizeBeforeDelete = ethnicityTypeRepository.findAll().size();

        // Get the ethnicityType
        restEthnicityTypeMockMvc.perform(delete("/api/ethnicity-types/{id}", ethnicityType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EthnicityType> ethnicityTypeList = ethnicityTypeRepository.findAll();
        assertThat(ethnicityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EthnicityType.class);
        EthnicityType ethnicityType1 = new EthnicityType();
        ethnicityType1.setId(1L);
        EthnicityType ethnicityType2 = new EthnicityType();
        ethnicityType2.setId(ethnicityType1.getId());
        assertThat(ethnicityType1).isEqualTo(ethnicityType2);
        ethnicityType2.setId(2L);
        assertThat(ethnicityType1).isNotEqualTo(ethnicityType2);
        ethnicityType1.setId(null);
        assertThat(ethnicityType1).isNotEqualTo(ethnicityType2);
    }
}
