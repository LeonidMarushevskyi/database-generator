package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.ApplicationStatusType;
import gov.ca.cwds.cals.rest.api.repository.ApplicationStatusTypeRepository;
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
 * Test class for the ApplicationStatusTypeResource REST controller.
 *
 * @see ApplicationStatusTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class ApplicationStatusTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ApplicationStatusTypeRepository applicationStatusTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationStatusTypeMockMvc;

    private ApplicationStatusType applicationStatusType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicationStatusTypeResource applicationStatusTypeResource = new ApplicationStatusTypeResource(applicationStatusTypeRepository);
        this.restApplicationStatusTypeMockMvc = MockMvcBuilders.standaloneSetup(applicationStatusTypeResource)
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
    public static ApplicationStatusType createEntity(EntityManager em) {
        ApplicationStatusType applicationStatusType = new ApplicationStatusType()
            .name(DEFAULT_NAME);
        return applicationStatusType;
    }

    @Before
    public void initTest() {
        applicationStatusType = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationStatusType() throws Exception {
        int databaseSizeBeforeCreate = applicationStatusTypeRepository.findAll().size();

        // Create the ApplicationStatusType
        restApplicationStatusTypeMockMvc.perform(post("/api/application-status-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationStatusType)))
            .andExpect(status().isCreated());

        // Validate the ApplicationStatusType in the database
        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationStatusType testApplicationStatusType = applicationStatusTypeList.get(applicationStatusTypeList.size() - 1);
        assertThat(testApplicationStatusType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createApplicationStatusTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationStatusTypeRepository.findAll().size();

        // Create the ApplicationStatusType with an existing ID
        applicationStatusType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationStatusTypeMockMvc.perform(post("/api/application-status-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationStatusType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationStatusTypeRepository.findAll().size();
        // set the field null
        applicationStatusType.setName(null);

        // Create the ApplicationStatusType, which fails.

        restApplicationStatusTypeMockMvc.perform(post("/api/application-status-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationStatusType)))
            .andExpect(status().isBadRequest());

        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationStatusTypes() throws Exception {
        // Initialize the database
        applicationStatusTypeRepository.saveAndFlush(applicationStatusType);

        // Get all the applicationStatusTypeList
        restApplicationStatusTypeMockMvc.perform(get("/api/application-status-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationStatusType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getApplicationStatusType() throws Exception {
        // Initialize the database
        applicationStatusTypeRepository.saveAndFlush(applicationStatusType);

        // Get the applicationStatusType
        restApplicationStatusTypeMockMvc.perform(get("/api/application-status-types/{id}", applicationStatusType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationStatusType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationStatusType() throws Exception {
        // Get the applicationStatusType
        restApplicationStatusTypeMockMvc.perform(get("/api/application-status-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationStatusType() throws Exception {
        // Initialize the database
        applicationStatusTypeRepository.saveAndFlush(applicationStatusType);
        int databaseSizeBeforeUpdate = applicationStatusTypeRepository.findAll().size();

        // Update the applicationStatusType
        ApplicationStatusType updatedApplicationStatusType = applicationStatusTypeRepository.findOne(applicationStatusType.getId());
        updatedApplicationStatusType
            .name(UPDATED_NAME);

        restApplicationStatusTypeMockMvc.perform(put("/api/application-status-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationStatusType)))
            .andExpect(status().isOk());

        // Validate the ApplicationStatusType in the database
        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationStatusType testApplicationStatusType = applicationStatusTypeList.get(applicationStatusTypeList.size() - 1);
        assertThat(testApplicationStatusType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationStatusType() throws Exception {
        int databaseSizeBeforeUpdate = applicationStatusTypeRepository.findAll().size();

        // Create the ApplicationStatusType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicationStatusTypeMockMvc.perform(put("/api/application-status-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationStatusType)))
            .andExpect(status().isCreated());

        // Validate the ApplicationStatusType in the database
        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicationStatusType() throws Exception {
        // Initialize the database
        applicationStatusTypeRepository.saveAndFlush(applicationStatusType);
        int databaseSizeBeforeDelete = applicationStatusTypeRepository.findAll().size();

        // Get the applicationStatusType
        restApplicationStatusTypeMockMvc.perform(delete("/api/application-status-types/{id}", applicationStatusType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationStatusType> applicationStatusTypeList = applicationStatusTypeRepository.findAll();
        assertThat(applicationStatusTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationStatusType.class);
        ApplicationStatusType applicationStatusType1 = new ApplicationStatusType();
        applicationStatusType1.setId(1L);
        ApplicationStatusType applicationStatusType2 = new ApplicationStatusType();
        applicationStatusType2.setId(applicationStatusType1.getId());
        assertThat(applicationStatusType1).isEqualTo(applicationStatusType2);
        applicationStatusType2.setId(2L);
        assertThat(applicationStatusType1).isNotEqualTo(applicationStatusType2);
        applicationStatusType1.setId(null);
        assertThat(applicationStatusType1).isNotEqualTo(applicationStatusType2);
    }
}
