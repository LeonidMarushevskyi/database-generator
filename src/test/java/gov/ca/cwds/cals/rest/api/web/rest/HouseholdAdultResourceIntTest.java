package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.HouseholdAdult;
import gov.ca.cwds.cals.rest.api.repository.HouseholdAdultRepository;
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
 * Test class for the HouseholdAdultResource REST controller.
 *
 * @see HouseholdAdultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class HouseholdAdultResourceIntTest {

    private static final Boolean DEFAULT_OUT_OF_STATE_DISCLOSURE_STATE = false;
    private static final Boolean UPDATED_OUT_OF_STATE_DISCLOSURE_STATE = true;

    private static final Boolean DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_1 = false;
    private static final Boolean UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_1 = true;

    private static final Boolean DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_2 = false;
    private static final Boolean UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_2 = true;

    private static final Boolean DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_3 = false;
    private static final Boolean UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_3 = true;

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HouseholdAdultRepository householdAdultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHouseholdAdultMockMvc;

    private HouseholdAdult householdAdult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HouseholdAdultResource householdAdultResource = new HouseholdAdultResource(householdAdultRepository);
        this.restHouseholdAdultMockMvc = MockMvcBuilders.standaloneSetup(householdAdultResource)
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
    public static HouseholdAdult createEntity(EntityManager em) {
        HouseholdAdult householdAdult = new HouseholdAdult()
            .outOfStateDisclosureState(DEFAULT_OUT_OF_STATE_DISCLOSURE_STATE)
            .criminalRecordStatementQuestion1(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_1)
            .criminalRecordStatementQuestion2(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_2)
            .criminalRecordStatementQuestion3(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_3)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return householdAdult;
    }

    @Before
    public void initTest() {
        householdAdult = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseholdAdult() throws Exception {
        int databaseSizeBeforeCreate = householdAdultRepository.findAll().size();

        // Create the HouseholdAdult
        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isCreated());

        // Validate the HouseholdAdult in the database
        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeCreate + 1);
        HouseholdAdult testHouseholdAdult = householdAdultList.get(householdAdultList.size() - 1);
        assertThat(testHouseholdAdult.isOutOfStateDisclosureState()).isEqualTo(DEFAULT_OUT_OF_STATE_DISCLOSURE_STATE);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion1()).isEqualTo(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_1);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion2()).isEqualTo(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_2);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion3()).isEqualTo(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_3);
        assertThat(testHouseholdAdult.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testHouseholdAdult.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testHouseholdAdult.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testHouseholdAdult.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createHouseholdAdultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = householdAdultRepository.findAll().size();

        // Create the HouseholdAdult with an existing ID
        householdAdult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOutOfStateDisclosureStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setOutOfStateDisclosureState(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCriminalRecordStatementQuestion1IsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setCriminalRecordStatementQuestion1(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCriminalRecordStatementQuestion2IsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setCriminalRecordStatementQuestion2(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCriminalRecordStatementQuestion3IsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setCriminalRecordStatementQuestion3(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setCreateUserId(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setCreateDateTime(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setUpdateUserId(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAdultRepository.findAll().size();
        // set the field null
        householdAdult.setUpdateDateTime(null);

        // Create the HouseholdAdult, which fails.

        restHouseholdAdultMockMvc.perform(post("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isBadRequest());

        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHouseholdAdults() throws Exception {
        // Initialize the database
        householdAdultRepository.saveAndFlush(householdAdult);

        // Get all the householdAdultList
        restHouseholdAdultMockMvc.perform(get("/api/household-adults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(householdAdult.getId().intValue())))
            .andExpect(jsonPath("$.[*].outOfStateDisclosureState").value(hasItem(DEFAULT_OUT_OF_STATE_DISCLOSURE_STATE.booleanValue())))
            .andExpect(jsonPath("$.[*].criminalRecordStatementQuestion1").value(hasItem(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_1.booleanValue())))
            .andExpect(jsonPath("$.[*].criminalRecordStatementQuestion2").value(hasItem(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_2.booleanValue())))
            .andExpect(jsonPath("$.[*].criminalRecordStatementQuestion3").value(hasItem(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_3.booleanValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getHouseholdAdult() throws Exception {
        // Initialize the database
        householdAdultRepository.saveAndFlush(householdAdult);

        // Get the householdAdult
        restHouseholdAdultMockMvc.perform(get("/api/household-adults/{id}", householdAdult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(householdAdult.getId().intValue()))
            .andExpect(jsonPath("$.outOfStateDisclosureState").value(DEFAULT_OUT_OF_STATE_DISCLOSURE_STATE.booleanValue()))
            .andExpect(jsonPath("$.criminalRecordStatementQuestion1").value(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_1.booleanValue()))
            .andExpect(jsonPath("$.criminalRecordStatementQuestion2").value(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_2.booleanValue()))
            .andExpect(jsonPath("$.criminalRecordStatementQuestion3").value(DEFAULT_CRIMINAL_RECORD_STATEMENT_QUESTION_3.booleanValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingHouseholdAdult() throws Exception {
        // Get the householdAdult
        restHouseholdAdultMockMvc.perform(get("/api/household-adults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseholdAdult() throws Exception {
        // Initialize the database
        householdAdultRepository.saveAndFlush(householdAdult);
        int databaseSizeBeforeUpdate = householdAdultRepository.findAll().size();

        // Update the householdAdult
        HouseholdAdult updatedHouseholdAdult = householdAdultRepository.findOne(householdAdult.getId());
        updatedHouseholdAdult
            .outOfStateDisclosureState(UPDATED_OUT_OF_STATE_DISCLOSURE_STATE)
            .criminalRecordStatementQuestion1(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_1)
            .criminalRecordStatementQuestion2(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_2)
            .criminalRecordStatementQuestion3(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_3)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restHouseholdAdultMockMvc.perform(put("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHouseholdAdult)))
            .andExpect(status().isOk());

        // Validate the HouseholdAdult in the database
        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeUpdate);
        HouseholdAdult testHouseholdAdult = householdAdultList.get(householdAdultList.size() - 1);
        assertThat(testHouseholdAdult.isOutOfStateDisclosureState()).isEqualTo(UPDATED_OUT_OF_STATE_DISCLOSURE_STATE);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion1()).isEqualTo(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_1);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion2()).isEqualTo(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_2);
        assertThat(testHouseholdAdult.isCriminalRecordStatementQuestion3()).isEqualTo(UPDATED_CRIMINAL_RECORD_STATEMENT_QUESTION_3);
        assertThat(testHouseholdAdult.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testHouseholdAdult.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testHouseholdAdult.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testHouseholdAdult.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseholdAdult() throws Exception {
        int databaseSizeBeforeUpdate = householdAdultRepository.findAll().size();

        // Create the HouseholdAdult

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHouseholdAdultMockMvc.perform(put("/api/household-adults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAdult)))
            .andExpect(status().isCreated());

        // Validate the HouseholdAdult in the database
        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHouseholdAdult() throws Exception {
        // Initialize the database
        householdAdultRepository.saveAndFlush(householdAdult);
        int databaseSizeBeforeDelete = householdAdultRepository.findAll().size();

        // Get the householdAdult
        restHouseholdAdultMockMvc.perform(delete("/api/household-adults/{id}", householdAdult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HouseholdAdult> householdAdultList = householdAdultRepository.findAll();
        assertThat(householdAdultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseholdAdult.class);
        HouseholdAdult householdAdult1 = new HouseholdAdult();
        householdAdult1.setId(1L);
        HouseholdAdult householdAdult2 = new HouseholdAdult();
        householdAdult2.setId(householdAdult1.getId());
        assertThat(householdAdult1).isEqualTo(householdAdult2);
        householdAdult2.setId(2L);
        assertThat(householdAdult1).isNotEqualTo(householdAdult2);
        householdAdult1.setId(null);
        assertThat(householdAdult1).isNotEqualTo(householdAdult2);
    }
}
