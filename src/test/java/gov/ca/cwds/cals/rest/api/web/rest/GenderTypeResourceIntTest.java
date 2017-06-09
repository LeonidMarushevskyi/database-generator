package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.GenderType;
import gov.ca.cwds.cals.rest.api.repository.GenderTypeRepository;
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
 * Test class for the GenderTypeResource REST controller.
 *
 * @see GenderTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class GenderTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GenderTypeRepository genderTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGenderTypeMockMvc;

    private GenderType genderType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenderTypeResource genderTypeResource = new GenderTypeResource(genderTypeRepository);
        this.restGenderTypeMockMvc = MockMvcBuilders.standaloneSetup(genderTypeResource)
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
    public static GenderType createEntity(EntityManager em) {
        GenderType genderType = new GenderType()
            .name(DEFAULT_NAME);
        return genderType;
    }

    @Before
    public void initTest() {
        genderType = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenderType() throws Exception {
        int databaseSizeBeforeCreate = genderTypeRepository.findAll().size();

        // Create the GenderType
        restGenderTypeMockMvc.perform(post("/api/gender-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genderType)))
            .andExpect(status().isCreated());

        // Validate the GenderType in the database
        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeCreate + 1);
        GenderType testGenderType = genderTypeList.get(genderTypeList.size() - 1);
        assertThat(testGenderType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGenderTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genderTypeRepository.findAll().size();

        // Create the GenderType with an existing ID
        genderType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenderTypeMockMvc.perform(post("/api/gender-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genderType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderTypeRepository.findAll().size();
        // set the field null
        genderType.setName(null);

        // Create the GenderType, which fails.

        restGenderTypeMockMvc.perform(post("/api/gender-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genderType)))
            .andExpect(status().isBadRequest());

        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenderTypes() throws Exception {
        // Initialize the database
        genderTypeRepository.saveAndFlush(genderType);

        // Get all the genderTypeList
        restGenderTypeMockMvc.perform(get("/api/gender-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genderType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGenderType() throws Exception {
        // Initialize the database
        genderTypeRepository.saveAndFlush(genderType);

        // Get the genderType
        restGenderTypeMockMvc.perform(get("/api/gender-types/{id}", genderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genderType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenderType() throws Exception {
        // Get the genderType
        restGenderTypeMockMvc.perform(get("/api/gender-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenderType() throws Exception {
        // Initialize the database
        genderTypeRepository.saveAndFlush(genderType);
        int databaseSizeBeforeUpdate = genderTypeRepository.findAll().size();

        // Update the genderType
        GenderType updatedGenderType = genderTypeRepository.findOne(genderType.getId());
        updatedGenderType
            .name(UPDATED_NAME);

        restGenderTypeMockMvc.perform(put("/api/gender-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenderType)))
            .andExpect(status().isOk());

        // Validate the GenderType in the database
        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeUpdate);
        GenderType testGenderType = genderTypeList.get(genderTypeList.size() - 1);
        assertThat(testGenderType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGenderType() throws Exception {
        int databaseSizeBeforeUpdate = genderTypeRepository.findAll().size();

        // Create the GenderType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenderTypeMockMvc.perform(put("/api/gender-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genderType)))
            .andExpect(status().isCreated());

        // Validate the GenderType in the database
        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGenderType() throws Exception {
        // Initialize the database
        genderTypeRepository.saveAndFlush(genderType);
        int databaseSizeBeforeDelete = genderTypeRepository.findAll().size();

        // Get the genderType
        restGenderTypeMockMvc.perform(delete("/api/gender-types/{id}", genderType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GenderType> genderTypeList = genderTypeRepository.findAll();
        assertThat(genderTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenderType.class);
        GenderType genderType1 = new GenderType();
        genderType1.setId(1L);
        GenderType genderType2 = new GenderType();
        genderType2.setId(genderType1.getId());
        assertThat(genderType1).isEqualTo(genderType2);
        genderType2.setId(2L);
        assertThat(genderType1).isNotEqualTo(genderType2);
        genderType1.setId(null);
        assertThat(genderType1).isNotEqualTo(genderType2);
    }
}
