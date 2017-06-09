package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.LicensureHistory;
import gov.ca.cwds.cals.rest.api.repository.LicensureHistoryRepository;
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
 * Test class for the LicensureHistoryResource REST controller.
 *
 * @see LicensureHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class LicensureHistoryResourceIntTest {

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_1 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_1 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_11 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_11 = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_12 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_12 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_2 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_2 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_21 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_21 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_3 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_3 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_31 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_31 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_4 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_4 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_41 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_41 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_5 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_5 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_51 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_51 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_6 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_6 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_61 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_61 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LICENSURE_HISTORY_QUESTION_7 = false;
    private static final Boolean UPDATED_LICENSURE_HISTORY_QUESTION_7 = true;

    private static final String DEFAULT_LICENSURE_HISTORY_QUESTION_71 = "AAAAAAAAAA";
    private static final String UPDATED_LICENSURE_HISTORY_QUESTION_71 = "BBBBBBBBBB";

    @Autowired
    private LicensureHistoryRepository licensureHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLicensureHistoryMockMvc;

    private LicensureHistory licensureHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LicensureHistoryResource licensureHistoryResource = new LicensureHistoryResource(licensureHistoryRepository);
        this.restLicensureHistoryMockMvc = MockMvcBuilders.standaloneSetup(licensureHistoryResource)
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
    public static LicensureHistory createEntity(EntityManager em) {
        LicensureHistory licensureHistory = new LicensureHistory()
            .licensureHistoryQuestion1(DEFAULT_LICENSURE_HISTORY_QUESTION_1)
            .licensureHistoryQuestion11(DEFAULT_LICENSURE_HISTORY_QUESTION_11)
            .licensureHistoryQuestion12(DEFAULT_LICENSURE_HISTORY_QUESTION_12)
            .licensureHistoryQuestion2(DEFAULT_LICENSURE_HISTORY_QUESTION_2)
            .licensureHistoryQuestion21(DEFAULT_LICENSURE_HISTORY_QUESTION_21)
            .licensureHistoryQuestion3(DEFAULT_LICENSURE_HISTORY_QUESTION_3)
            .licensureHistoryQuestion31(DEFAULT_LICENSURE_HISTORY_QUESTION_31)
            .licensureHistoryQuestion4(DEFAULT_LICENSURE_HISTORY_QUESTION_4)
            .licensureHistoryQuestion41(DEFAULT_LICENSURE_HISTORY_QUESTION_41)
            .licensureHistoryQuestion5(DEFAULT_LICENSURE_HISTORY_QUESTION_5)
            .licensureHistoryQuestion51(DEFAULT_LICENSURE_HISTORY_QUESTION_51)
            .licensureHistoryQuestion6(DEFAULT_LICENSURE_HISTORY_QUESTION_6)
            .licensureHistoryQuestion61(DEFAULT_LICENSURE_HISTORY_QUESTION_61)
            .licensureHistoryQuestion7(DEFAULT_LICENSURE_HISTORY_QUESTION_7)
            .licensureHistoryQuestion71(DEFAULT_LICENSURE_HISTORY_QUESTION_71);
        return licensureHistory;
    }

    @Before
    public void initTest() {
        licensureHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicensureHistory() throws Exception {
        int databaseSizeBeforeCreate = licensureHistoryRepository.findAll().size();

        // Create the LicensureHistory
        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isCreated());

        // Validate the LicensureHistory in the database
        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LicensureHistory testLicensureHistory = licensureHistoryList.get(licensureHistoryList.size() - 1);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion1()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_1);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion11()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_11);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion12()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_12);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion2()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_2);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion21()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_21);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion3()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_3);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion31()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_31);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion4()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_4);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion41()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_41);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion5()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_5);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion51()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_51);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion6()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_6);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion61()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_61);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion7()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_7);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion71()).isEqualTo(DEFAULT_LICENSURE_HISTORY_QUESTION_71);
    }

    @Test
    @Transactional
    public void createLicensureHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licensureHistoryRepository.findAll().size();

        // Create the LicensureHistory with an existing ID
        licensureHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion1IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion1(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion2IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion2(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion3IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion3(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion4IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion4(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion5IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion5(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion6IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion6(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensureHistoryQuestion7IsRequired() throws Exception {
        int databaseSizeBeforeTest = licensureHistoryRepository.findAll().size();
        // set the field null
        licensureHistory.setLicensureHistoryQuestion7(null);

        // Create the LicensureHistory, which fails.

        restLicensureHistoryMockMvc.perform(post("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isBadRequest());

        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicensureHistories() throws Exception {
        // Initialize the database
        licensureHistoryRepository.saveAndFlush(licensureHistory);

        // Get all the licensureHistoryList
        restLicensureHistoryMockMvc.perform(get("/api/licensure-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licensureHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion1").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_1.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion11").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_11.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion12").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_12.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion2").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_2.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion21").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_21.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion3").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_3.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion31").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_31.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion4").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_4.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion41").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_41.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion5").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_5.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion51").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_51.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion6").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_6.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion61").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_61.toString())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion7").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_7.booleanValue())))
            .andExpect(jsonPath("$.[*].licensureHistoryQuestion71").value(hasItem(DEFAULT_LICENSURE_HISTORY_QUESTION_71.toString())));
    }

    @Test
    @Transactional
    public void getLicensureHistory() throws Exception {
        // Initialize the database
        licensureHistoryRepository.saveAndFlush(licensureHistory);

        // Get the licensureHistory
        restLicensureHistoryMockMvc.perform(get("/api/licensure-histories/{id}", licensureHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licensureHistory.getId().intValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion1").value(DEFAULT_LICENSURE_HISTORY_QUESTION_1.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion11").value(DEFAULT_LICENSURE_HISTORY_QUESTION_11.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion12").value(DEFAULT_LICENSURE_HISTORY_QUESTION_12.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion2").value(DEFAULT_LICENSURE_HISTORY_QUESTION_2.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion21").value(DEFAULT_LICENSURE_HISTORY_QUESTION_21.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion3").value(DEFAULT_LICENSURE_HISTORY_QUESTION_3.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion31").value(DEFAULT_LICENSURE_HISTORY_QUESTION_31.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion4").value(DEFAULT_LICENSURE_HISTORY_QUESTION_4.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion41").value(DEFAULT_LICENSURE_HISTORY_QUESTION_41.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion5").value(DEFAULT_LICENSURE_HISTORY_QUESTION_5.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion51").value(DEFAULT_LICENSURE_HISTORY_QUESTION_51.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion6").value(DEFAULT_LICENSURE_HISTORY_QUESTION_6.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion61").value(DEFAULT_LICENSURE_HISTORY_QUESTION_61.toString()))
            .andExpect(jsonPath("$.licensureHistoryQuestion7").value(DEFAULT_LICENSURE_HISTORY_QUESTION_7.booleanValue()))
            .andExpect(jsonPath("$.licensureHistoryQuestion71").value(DEFAULT_LICENSURE_HISTORY_QUESTION_71.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLicensureHistory() throws Exception {
        // Get the licensureHistory
        restLicensureHistoryMockMvc.perform(get("/api/licensure-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicensureHistory() throws Exception {
        // Initialize the database
        licensureHistoryRepository.saveAndFlush(licensureHistory);
        int databaseSizeBeforeUpdate = licensureHistoryRepository.findAll().size();

        // Update the licensureHistory
        LicensureHistory updatedLicensureHistory = licensureHistoryRepository.findOne(licensureHistory.getId());
        updatedLicensureHistory
            .licensureHistoryQuestion1(UPDATED_LICENSURE_HISTORY_QUESTION_1)
            .licensureHistoryQuestion11(UPDATED_LICENSURE_HISTORY_QUESTION_11)
            .licensureHistoryQuestion12(UPDATED_LICENSURE_HISTORY_QUESTION_12)
            .licensureHistoryQuestion2(UPDATED_LICENSURE_HISTORY_QUESTION_2)
            .licensureHistoryQuestion21(UPDATED_LICENSURE_HISTORY_QUESTION_21)
            .licensureHistoryQuestion3(UPDATED_LICENSURE_HISTORY_QUESTION_3)
            .licensureHistoryQuestion31(UPDATED_LICENSURE_HISTORY_QUESTION_31)
            .licensureHistoryQuestion4(UPDATED_LICENSURE_HISTORY_QUESTION_4)
            .licensureHistoryQuestion41(UPDATED_LICENSURE_HISTORY_QUESTION_41)
            .licensureHistoryQuestion5(UPDATED_LICENSURE_HISTORY_QUESTION_5)
            .licensureHistoryQuestion51(UPDATED_LICENSURE_HISTORY_QUESTION_51)
            .licensureHistoryQuestion6(UPDATED_LICENSURE_HISTORY_QUESTION_6)
            .licensureHistoryQuestion61(UPDATED_LICENSURE_HISTORY_QUESTION_61)
            .licensureHistoryQuestion7(UPDATED_LICENSURE_HISTORY_QUESTION_7)
            .licensureHistoryQuestion71(UPDATED_LICENSURE_HISTORY_QUESTION_71);

        restLicensureHistoryMockMvc.perform(put("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLicensureHistory)))
            .andExpect(status().isOk());

        // Validate the LicensureHistory in the database
        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeUpdate);
        LicensureHistory testLicensureHistory = licensureHistoryList.get(licensureHistoryList.size() - 1);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion1()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_1);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion11()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_11);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion12()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_12);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion2()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_2);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion21()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_21);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion3()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_3);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion31()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_31);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion4()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_4);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion41()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_41);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion5()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_5);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion51()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_51);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion6()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_6);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion61()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_61);
        assertThat(testLicensureHistory.isLicensureHistoryQuestion7()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_7);
        assertThat(testLicensureHistory.getLicensureHistoryQuestion71()).isEqualTo(UPDATED_LICENSURE_HISTORY_QUESTION_71);
    }

    @Test
    @Transactional
    public void updateNonExistingLicensureHistory() throws Exception {
        int databaseSizeBeforeUpdate = licensureHistoryRepository.findAll().size();

        // Create the LicensureHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLicensureHistoryMockMvc.perform(put("/api/licensure-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licensureHistory)))
            .andExpect(status().isCreated());

        // Validate the LicensureHistory in the database
        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLicensureHistory() throws Exception {
        // Initialize the database
        licensureHistoryRepository.saveAndFlush(licensureHistory);
        int databaseSizeBeforeDelete = licensureHistoryRepository.findAll().size();

        // Get the licensureHistory
        restLicensureHistoryMockMvc.perform(delete("/api/licensure-histories/{id}", licensureHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LicensureHistory> licensureHistoryList = licensureHistoryRepository.findAll();
        assertThat(licensureHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicensureHistory.class);
        LicensureHistory licensureHistory1 = new LicensureHistory();
        licensureHistory1.setId(1L);
        LicensureHistory licensureHistory2 = new LicensureHistory();
        licensureHistory2.setId(licensureHistory1.getId());
        assertThat(licensureHistory1).isEqualTo(licensureHistory2);
        licensureHistory2.setId(2L);
        assertThat(licensureHistory1).isNotEqualTo(licensureHistory2);
        licensureHistory1.setId(null);
        assertThat(licensureHistory1).isNotEqualTo(licensureHistory2);
    }
}
