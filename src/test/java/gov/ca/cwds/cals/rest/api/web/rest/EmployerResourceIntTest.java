package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Employer;
import gov.ca.cwds.cals.rest.api.repository.EmployerRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static gov.ca.cwds.cals.rest.api.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployerResource REST controller.
 *
 * @see EmployerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EmployerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployerMockMvc;

    private Employer employer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployerResource employerResource = new EmployerResource(employerRepository);
        this.restEmployerMockMvc = MockMvcBuilders.standaloneSetup(employerResource)
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
    public static Employer createEntity(EntityManager em) {
        Employer employer = new Employer()
            .name(DEFAULT_NAME)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return employer;
    }

    @Before
    public void initTest() {
        employer = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployer() throws Exception {
        int databaseSizeBeforeCreate = employerRepository.findAll().size();

        // Create the Employer
        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isCreated());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeCreate + 1);
        Employer testEmployer = employerList.get(employerList.size() - 1);
        assertThat(testEmployer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployer.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testEmployer.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testEmployer.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testEmployer.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createEmployerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employerRepository.findAll().size();

        // Create the Employer with an existing ID
        employer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setCreateUserId(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setCreateDateTime(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setUpdateUserId(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setUpdateDateTime(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployers() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get all the employerList
        restEmployerMockMvc.perform(get("/api/employers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", employer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEmployer() throws Exception {
        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);
        int databaseSizeBeforeUpdate = employerRepository.findAll().size();

        // Update the employer
        Employer updatedEmployer = employerRepository.findOne(employer.getId());
        updatedEmployer
            .name(UPDATED_NAME)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restEmployerMockMvc.perform(put("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployer)))
            .andExpect(status().isOk());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeUpdate);
        Employer testEmployer = employerList.get(employerList.size() - 1);
        assertThat(testEmployer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployer.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testEmployer.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testEmployer.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testEmployer.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployer() throws Exception {
        int databaseSizeBeforeUpdate = employerRepository.findAll().size();

        // Create the Employer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployerMockMvc.perform(put("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isCreated());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);
        int databaseSizeBeforeDelete = employerRepository.findAll().size();

        // Get the employer
        restEmployerMockMvc.perform(delete("/api/employers/{id}", employer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employer.class);
        Employer employer1 = new Employer();
        employer1.setId(1L);
        Employer employer2 = new Employer();
        employer2.setId(employer1.getId());
        assertThat(employer1).isEqualTo(employer2);
        employer2.setId(2L);
        assertThat(employer1).isNotEqualTo(employer2);
        employer1.setId(null);
        assertThat(employer1).isNotEqualTo(employer2);
    }
}
