package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.FacilityType;
import gov.ca.cwds.cals.rest.api.repository.FacilityTypeRepository;
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
 * Test class for the FacilityTypeResource REST controller.
 *
 * @see FacilityTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityTypeResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityTypeMockMvc;

    private FacilityType facilityType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityTypeResource facilityTypeResource = new FacilityTypeResource(facilityTypeRepository);
        this.restFacilityTypeMockMvc = MockMvcBuilders.standaloneSetup(facilityTypeResource)
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
    public static FacilityType createEntity(EntityManager em) {
        FacilityType facilityType = new FacilityType()
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE);
        return facilityType;
    }

    @Before
    public void initTest() {
        facilityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityType() throws Exception {
        int databaseSizeBeforeCreate = facilityTypeRepository.findAll().size();

        // Create the FacilityType
        restFacilityTypeMockMvc.perform(post("/api/facility-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityType)))
            .andExpect(status().isCreated());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityType testFacilityType = facilityTypeList.get(facilityTypeList.size() - 1);
        assertThat(testFacilityType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFacilityType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createFacilityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityTypeRepository.findAll().size();

        // Create the FacilityType with an existing ID
        facilityType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityTypeMockMvc.perform(post("/api/facility-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityTypeRepository.findAll().size();
        // set the field null
        facilityType.setCode(null);

        // Create the FacilityType, which fails.

        restFacilityTypeMockMvc.perform(post("/api/facility-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityType)))
            .andExpect(status().isBadRequest());

        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityTypes() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        // Get all the facilityTypeList
        restFacilityTypeMockMvc.perform(get("/api/facility-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        // Get the facilityType
        restFacilityTypeMockMvc.perform(get("/api/facility-types/{id}", facilityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facilityType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityType() throws Exception {
        // Get the facilityType
        restFacilityTypeMockMvc.perform(get("/api/facility-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);
        int databaseSizeBeforeUpdate = facilityTypeRepository.findAll().size();

        // Update the facilityType
        FacilityType updatedFacilityType = facilityTypeRepository.findOne(facilityType.getId());
        updatedFacilityType
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE);

        restFacilityTypeMockMvc.perform(put("/api/facility-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityType)))
            .andExpect(status().isOk());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeUpdate);
        FacilityType testFacilityType = facilityTypeList.get(facilityTypeList.size() - 1);
        assertThat(testFacilityType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFacilityType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityType() throws Exception {
        int databaseSizeBeforeUpdate = facilityTypeRepository.findAll().size();

        // Create the FacilityType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityTypeMockMvc.perform(put("/api/facility-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityType)))
            .andExpect(status().isCreated());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);
        int databaseSizeBeforeDelete = facilityTypeRepository.findAll().size();

        // Get the facilityType
        restFacilityTypeMockMvc.perform(delete("/api/facility-types/{id}", facilityType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityType.class);
    }
}
