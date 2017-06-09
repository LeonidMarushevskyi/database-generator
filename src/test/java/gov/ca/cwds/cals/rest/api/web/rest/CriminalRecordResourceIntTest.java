package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.CriminalRecord;
import gov.ca.cwds.cals.rest.api.repository.CriminalRecordRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
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
 * Test class for the CriminalRecordResource REST controller.
 *
 * @see CriminalRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class CriminalRecordResourceIntTest {

    private static final String DEFAULT_OFFENSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OFFENSE_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_OFFENSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFENSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OFFENSE_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_OFFENSE_EXPLANATION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CriminalRecordRepository criminalRecordRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCriminalRecordMockMvc;

    private CriminalRecord criminalRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CriminalRecordResource criminalRecordResource = new CriminalRecordResource(criminalRecordRepository);
        this.restCriminalRecordMockMvc = MockMvcBuilders.standaloneSetup(criminalRecordResource)
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
    public static CriminalRecord createEntity(EntityManager em) {
        CriminalRecord criminalRecord = new CriminalRecord()
            .offenseDescription(DEFAULT_OFFENSE_DESCRIPTION)
            .offenseDate(DEFAULT_OFFENSE_DATE)
            .offenseExplanation(DEFAULT_OFFENSE_EXPLANATION)
            .city(DEFAULT_CITY)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return criminalRecord;
    }

    @Before
    public void initTest() {
        criminalRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createCriminalRecord() throws Exception {
        int databaseSizeBeforeCreate = criminalRecordRepository.findAll().size();

        // Create the CriminalRecord
        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isCreated());

        // Validate the CriminalRecord in the database
        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeCreate + 1);
        CriminalRecord testCriminalRecord = criminalRecordList.get(criminalRecordList.size() - 1);
        assertThat(testCriminalRecord.getOffenseDescription()).isEqualTo(DEFAULT_OFFENSE_DESCRIPTION);
        assertThat(testCriminalRecord.getOffenseDate()).isEqualTo(DEFAULT_OFFENSE_DATE);
        assertThat(testCriminalRecord.getOffenseExplanation()).isEqualTo(DEFAULT_OFFENSE_EXPLANATION);
        assertThat(testCriminalRecord.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCriminalRecord.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testCriminalRecord.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testCriminalRecord.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testCriminalRecord.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createCriminalRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = criminalRecordRepository.findAll().size();

        // Create the CriminalRecord with an existing ID
        criminalRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOffenseDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setOffenseDescription(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOffenseExplanationIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setOffenseExplanation(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setCreateUserId(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setCreateDateTime(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setUpdateUserId(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = criminalRecordRepository.findAll().size();
        // set the field null
        criminalRecord.setUpdateDateTime(null);

        // Create the CriminalRecord, which fails.

        restCriminalRecordMockMvc.perform(post("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isBadRequest());

        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCriminalRecords() throws Exception {
        // Initialize the database
        criminalRecordRepository.saveAndFlush(criminalRecord);

        // Get all the criminalRecordList
        restCriminalRecordMockMvc.perform(get("/api/criminal-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criminalRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].offenseDescription").value(hasItem(DEFAULT_OFFENSE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].offenseDate").value(hasItem(DEFAULT_OFFENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].offenseExplanation").value(hasItem(DEFAULT_OFFENSE_EXPLANATION.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getCriminalRecord() throws Exception {
        // Initialize the database
        criminalRecordRepository.saveAndFlush(criminalRecord);

        // Get the criminalRecord
        restCriminalRecordMockMvc.perform(get("/api/criminal-records/{id}", criminalRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(criminalRecord.getId().intValue()))
            .andExpect(jsonPath("$.offenseDescription").value(DEFAULT_OFFENSE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.offenseDate").value(DEFAULT_OFFENSE_DATE.toString()))
            .andExpect(jsonPath("$.offenseExplanation").value(DEFAULT_OFFENSE_EXPLANATION.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingCriminalRecord() throws Exception {
        // Get the criminalRecord
        restCriminalRecordMockMvc.perform(get("/api/criminal-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCriminalRecord() throws Exception {
        // Initialize the database
        criminalRecordRepository.saveAndFlush(criminalRecord);
        int databaseSizeBeforeUpdate = criminalRecordRepository.findAll().size();

        // Update the criminalRecord
        CriminalRecord updatedCriminalRecord = criminalRecordRepository.findOne(criminalRecord.getId());
        updatedCriminalRecord
            .offenseDescription(UPDATED_OFFENSE_DESCRIPTION)
            .offenseDate(UPDATED_OFFENSE_DATE)
            .offenseExplanation(UPDATED_OFFENSE_EXPLANATION)
            .city(UPDATED_CITY)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restCriminalRecordMockMvc.perform(put("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCriminalRecord)))
            .andExpect(status().isOk());

        // Validate the CriminalRecord in the database
        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeUpdate);
        CriminalRecord testCriminalRecord = criminalRecordList.get(criminalRecordList.size() - 1);
        assertThat(testCriminalRecord.getOffenseDescription()).isEqualTo(UPDATED_OFFENSE_DESCRIPTION);
        assertThat(testCriminalRecord.getOffenseDate()).isEqualTo(UPDATED_OFFENSE_DATE);
        assertThat(testCriminalRecord.getOffenseExplanation()).isEqualTo(UPDATED_OFFENSE_EXPLANATION);
        assertThat(testCriminalRecord.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCriminalRecord.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testCriminalRecord.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testCriminalRecord.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testCriminalRecord.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCriminalRecord() throws Exception {
        int databaseSizeBeforeUpdate = criminalRecordRepository.findAll().size();

        // Create the CriminalRecord

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCriminalRecordMockMvc.perform(put("/api/criminal-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criminalRecord)))
            .andExpect(status().isCreated());

        // Validate the CriminalRecord in the database
        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCriminalRecord() throws Exception {
        // Initialize the database
        criminalRecordRepository.saveAndFlush(criminalRecord);
        int databaseSizeBeforeDelete = criminalRecordRepository.findAll().size();

        // Get the criminalRecord
        restCriminalRecordMockMvc.perform(delete("/api/criminal-records/{id}", criminalRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CriminalRecord> criminalRecordList = criminalRecordRepository.findAll();
        assertThat(criminalRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CriminalRecord.class);
        CriminalRecord criminalRecord1 = new CriminalRecord();
        criminalRecord1.setId(1L);
        CriminalRecord criminalRecord2 = new CriminalRecord();
        criminalRecord2.setId(criminalRecord1.getId());
        assertThat(criminalRecord1).isEqualTo(criminalRecord2);
        criminalRecord2.setId(2L);
        assertThat(criminalRecord1).isNotEqualTo(criminalRecord2);
        criminalRecord1.setId(null);
        assertThat(criminalRecord1).isNotEqualTo(criminalRecord2);
    }
}
