package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Employment;
import gov.ca.cwds.cals.rest.api.repository.EmploymentRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * Test class for the EmploymentResource REST controller.
 *
 * @see EmploymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EmploymentResourceIntTest {

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ANNUAL_INCOME = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUAL_INCOME = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EmploymentRepository employmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmploymentMockMvc;

    private Employment employment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmploymentResource employmentResource = new EmploymentResource(employmentRepository);
        this.restEmploymentMockMvc = MockMvcBuilders.standaloneSetup(employmentResource)
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
    public static Employment createEntity(EntityManager em) {
        Employment employment = new Employment()
            .occupation(DEFAULT_OCCUPATION)
            .annualIncome(DEFAULT_ANNUAL_INCOME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return employment;
    }

    @Before
    public void initTest() {
        employment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployment() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate + 1);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testEmployment.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testEmployment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployment.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testEmployment.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testEmployment.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testEmployment.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createEmploymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment with an existing ID
        employment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setOccupation(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnnualIncomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setAnnualIncome(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setStartDate(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setCreateUserId(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setCreateDateTime(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setUpdateUserId(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setUpdateDateTime(null);

        // Create the Employment, which fails.

        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployments() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get all the employmentList
        restEmploymentMockMvc.perform(get("/api/employments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employment.getId().intValue())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", employment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employment.getId().intValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.annualIncome").value(DEFAULT_ANNUAL_INCOME.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEmployment() throws Exception {
        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment
        Employment updatedEmployment = employmentRepository.findOne(employment.getId());
        updatedEmployment
            .occupation(UPDATED_OCCUPATION)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployment)))
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testEmployment.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testEmployment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployment.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testEmployment.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testEmployment.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testEmployment.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Create the Employment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);
        int databaseSizeBeforeDelete = employmentRepository.findAll().size();

        // Get the employment
        restEmploymentMockMvc.perform(delete("/api/employments/{id}", employment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employment.class);
        Employment employment1 = new Employment();
        employment1.setId(1L);
        Employment employment2 = new Employment();
        employment2.setId(employment1.getId());
        assertThat(employment1).isEqualTo(employment2);
        employment2.setId(2L);
        assertThat(employment1).isNotEqualTo(employment2);
        employment1.setId(null);
        assertThat(employment1).isNotEqualTo(employment2);
    }
}
