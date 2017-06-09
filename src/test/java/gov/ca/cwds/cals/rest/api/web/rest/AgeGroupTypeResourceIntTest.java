package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.AgeGroupType;
import gov.ca.cwds.cals.rest.api.repository.AgeGroupTypeRepository;
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
 * Test class for the AgeGroupTypeResource REST controller.
 *
 * @see AgeGroupTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class AgeGroupTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AgeGroupTypeRepository ageGroupTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgeGroupTypeMockMvc;

    private AgeGroupType ageGroupType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgeGroupTypeResource ageGroupTypeResource = new AgeGroupTypeResource(ageGroupTypeRepository);
        this.restAgeGroupTypeMockMvc = MockMvcBuilders.standaloneSetup(ageGroupTypeResource)
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
    public static AgeGroupType createEntity(EntityManager em) {
        AgeGroupType ageGroupType = new AgeGroupType()
            .name(DEFAULT_NAME);
        return ageGroupType;
    }

    @Before
    public void initTest() {
        ageGroupType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgeGroupType() throws Exception {
        int databaseSizeBeforeCreate = ageGroupTypeRepository.findAll().size();

        // Create the AgeGroupType
        restAgeGroupTypeMockMvc.perform(post("/api/age-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageGroupType)))
            .andExpect(status().isCreated());

        // Validate the AgeGroupType in the database
        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AgeGroupType testAgeGroupType = ageGroupTypeList.get(ageGroupTypeList.size() - 1);
        assertThat(testAgeGroupType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAgeGroupTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ageGroupTypeRepository.findAll().size();

        // Create the AgeGroupType with an existing ID
        ageGroupType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgeGroupTypeMockMvc.perform(post("/api/age-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageGroupType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageGroupTypeRepository.findAll().size();
        // set the field null
        ageGroupType.setName(null);

        // Create the AgeGroupType, which fails.

        restAgeGroupTypeMockMvc.perform(post("/api/age-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageGroupType)))
            .andExpect(status().isBadRequest());

        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgeGroupTypes() throws Exception {
        // Initialize the database
        ageGroupTypeRepository.saveAndFlush(ageGroupType);

        // Get all the ageGroupTypeList
        restAgeGroupTypeMockMvc.perform(get("/api/age-group-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ageGroupType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAgeGroupType() throws Exception {
        // Initialize the database
        ageGroupTypeRepository.saveAndFlush(ageGroupType);

        // Get the ageGroupType
        restAgeGroupTypeMockMvc.perform(get("/api/age-group-types/{id}", ageGroupType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ageGroupType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgeGroupType() throws Exception {
        // Get the ageGroupType
        restAgeGroupTypeMockMvc.perform(get("/api/age-group-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgeGroupType() throws Exception {
        // Initialize the database
        ageGroupTypeRepository.saveAndFlush(ageGroupType);
        int databaseSizeBeforeUpdate = ageGroupTypeRepository.findAll().size();

        // Update the ageGroupType
        AgeGroupType updatedAgeGroupType = ageGroupTypeRepository.findOne(ageGroupType.getId());
        updatedAgeGroupType
            .name(UPDATED_NAME);

        restAgeGroupTypeMockMvc.perform(put("/api/age-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgeGroupType)))
            .andExpect(status().isOk());

        // Validate the AgeGroupType in the database
        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeUpdate);
        AgeGroupType testAgeGroupType = ageGroupTypeList.get(ageGroupTypeList.size() - 1);
        assertThat(testAgeGroupType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAgeGroupType() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupTypeRepository.findAll().size();

        // Create the AgeGroupType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgeGroupTypeMockMvc.perform(put("/api/age-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageGroupType)))
            .andExpect(status().isCreated());

        // Validate the AgeGroupType in the database
        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgeGroupType() throws Exception {
        // Initialize the database
        ageGroupTypeRepository.saveAndFlush(ageGroupType);
        int databaseSizeBeforeDelete = ageGroupTypeRepository.findAll().size();

        // Get the ageGroupType
        restAgeGroupTypeMockMvc.perform(delete("/api/age-group-types/{id}", ageGroupType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgeGroupType> ageGroupTypeList = ageGroupTypeRepository.findAll();
        assertThat(ageGroupTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgeGroupType.class);
        AgeGroupType ageGroupType1 = new AgeGroupType();
        ageGroupType1.setId(1L);
        AgeGroupType ageGroupType2 = new AgeGroupType();
        ageGroupType2.setId(ageGroupType1.getId());
        assertThat(ageGroupType1).isEqualTo(ageGroupType2);
        ageGroupType2.setId(2L);
        assertThat(ageGroupType1).isNotEqualTo(ageGroupType2);
        ageGroupType1.setId(null);
        assertThat(ageGroupType1).isNotEqualTo(ageGroupType2);
    }
}
