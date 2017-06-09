package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.EducationLevelType;
import gov.ca.cwds.cals.rest.api.repository.EducationLevelTypeRepository;
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
 * Test class for the EducationLevelTypeResource REST controller.
 *
 * @see EducationLevelTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EducationLevelTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private EducationLevelTypeRepository educationLevelTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEducationLevelTypeMockMvc;

    private EducationLevelType educationLevelType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationLevelTypeResource educationLevelTypeResource = new EducationLevelTypeResource(educationLevelTypeRepository);
        this.restEducationLevelTypeMockMvc = MockMvcBuilders.standaloneSetup(educationLevelTypeResource)
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
    public static EducationLevelType createEntity(EntityManager em) {
        EducationLevelType educationLevelType = new EducationLevelType()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL);
        return educationLevelType;
    }

    @Before
    public void initTest() {
        educationLevelType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationLevelType() throws Exception {
        int databaseSizeBeforeCreate = educationLevelTypeRepository.findAll().size();

        // Create the EducationLevelType
        restEducationLevelTypeMockMvc.perform(post("/api/education-level-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevelType)))
            .andExpect(status().isCreated());

        // Validate the EducationLevelType in the database
        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EducationLevelType testEducationLevelType = educationLevelTypeList.get(educationLevelTypeList.size() - 1);
        assertThat(testEducationLevelType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEducationLevelType.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createEducationLevelTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationLevelTypeRepository.findAll().size();

        // Create the EducationLevelType with an existing ID
        educationLevelType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationLevelTypeMockMvc.perform(post("/api/education-level-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevelType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationLevelTypeRepository.findAll().size();
        // set the field null
        educationLevelType.setName(null);

        // Create the EducationLevelType, which fails.

        restEducationLevelTypeMockMvc.perform(post("/api/education-level-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevelType)))
            .andExpect(status().isBadRequest());

        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducationLevelTypes() throws Exception {
        // Initialize the database
        educationLevelTypeRepository.saveAndFlush(educationLevelType);

        // Get all the educationLevelTypeList
        restEducationLevelTypeMockMvc.perform(get("/api/education-level-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationLevelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getEducationLevelType() throws Exception {
        // Initialize the database
        educationLevelTypeRepository.saveAndFlush(educationLevelType);

        // Get the educationLevelType
        restEducationLevelTypeMockMvc.perform(get("/api/education-level-types/{id}", educationLevelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationLevelType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingEducationLevelType() throws Exception {
        // Get the educationLevelType
        restEducationLevelTypeMockMvc.perform(get("/api/education-level-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationLevelType() throws Exception {
        // Initialize the database
        educationLevelTypeRepository.saveAndFlush(educationLevelType);
        int databaseSizeBeforeUpdate = educationLevelTypeRepository.findAll().size();

        // Update the educationLevelType
        EducationLevelType updatedEducationLevelType = educationLevelTypeRepository.findOne(educationLevelType.getId());
        updatedEducationLevelType
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL);

        restEducationLevelTypeMockMvc.perform(put("/api/education-level-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationLevelType)))
            .andExpect(status().isOk());

        // Validate the EducationLevelType in the database
        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeUpdate);
        EducationLevelType testEducationLevelType = educationLevelTypeList.get(educationLevelTypeList.size() - 1);
        assertThat(testEducationLevelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationLevelType.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationLevelType() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelTypeRepository.findAll().size();

        // Create the EducationLevelType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationLevelTypeMockMvc.perform(put("/api/education-level-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevelType)))
            .andExpect(status().isCreated());

        // Validate the EducationLevelType in the database
        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationLevelType() throws Exception {
        // Initialize the database
        educationLevelTypeRepository.saveAndFlush(educationLevelType);
        int databaseSizeBeforeDelete = educationLevelTypeRepository.findAll().size();

        // Get the educationLevelType
        restEducationLevelTypeMockMvc.perform(delete("/api/education-level-types/{id}", educationLevelType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationLevelType> educationLevelTypeList = educationLevelTypeRepository.findAll();
        assertThat(educationLevelTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationLevelType.class);
        EducationLevelType educationLevelType1 = new EducationLevelType();
        educationLevelType1.setId(1L);
        EducationLevelType educationLevelType2 = new EducationLevelType();
        educationLevelType2.setId(educationLevelType1.getId());
        assertThat(educationLevelType1).isEqualTo(educationLevelType2);
        educationLevelType2.setId(2L);
        assertThat(educationLevelType1).isNotEqualTo(educationLevelType2);
        educationLevelType1.setId(null);
        assertThat(educationLevelType1).isNotEqualTo(educationLevelType2);
    }
}
