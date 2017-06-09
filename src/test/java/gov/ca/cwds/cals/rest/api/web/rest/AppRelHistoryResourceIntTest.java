package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.AppRelHistory;
import gov.ca.cwds.cals.rest.api.domain.RelationshipEvent;
import gov.ca.cwds.cals.rest.api.domain.RelationshipEvent;
import gov.ca.cwds.cals.rest.api.repository.AppRelHistoryRepository;
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
 * Test class for the AppRelHistoryResource REST controller.
 *
 * @see AppRelHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class AppRelHistoryResourceIntTest {

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AppRelHistoryRepository appRelHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppRelHistoryMockMvc;

    private AppRelHistory appRelHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppRelHistoryResource appRelHistoryResource = new AppRelHistoryResource(appRelHistoryRepository);
        this.restAppRelHistoryMockMvc = MockMvcBuilders.standaloneSetup(appRelHistoryResource)
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
    public static AppRelHistory createEntity(EntityManager em) {
        AppRelHistory appRelHistory = new AppRelHistory()
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        // Add required entity
        RelationshipEvent startEvent = RelationshipEventResourceIntTest.createEntity(em);
        em.persist(startEvent);
        em.flush();
        appRelHistory.setStartEvent(startEvent);
        // Add required entity
        RelationshipEvent endEvent = RelationshipEventResourceIntTest.createEntity(em);
        em.persist(endEvent);
        em.flush();
        appRelHistory.setEndEvent(endEvent);
        return appRelHistory;
    }

    @Before
    public void initTest() {
        appRelHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppRelHistory() throws Exception {
        int databaseSizeBeforeCreate = appRelHistoryRepository.findAll().size();

        // Create the AppRelHistory
        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isCreated());

        // Validate the AppRelHistory in the database
        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        AppRelHistory testAppRelHistory = appRelHistoryList.get(appRelHistoryList.size() - 1);
        assertThat(testAppRelHistory.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testAppRelHistory.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testAppRelHistory.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testAppRelHistory.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createAppRelHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appRelHistoryRepository.findAll().size();

        // Create the AppRelHistory with an existing ID
        appRelHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRelHistoryRepository.findAll().size();
        // set the field null
        appRelHistory.setCreateUserId(null);

        // Create the AppRelHistory, which fails.

        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isBadRequest());

        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRelHistoryRepository.findAll().size();
        // set the field null
        appRelHistory.setCreateDateTime(null);

        // Create the AppRelHistory, which fails.

        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isBadRequest());

        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRelHistoryRepository.findAll().size();
        // set the field null
        appRelHistory.setUpdateUserId(null);

        // Create the AppRelHistory, which fails.

        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isBadRequest());

        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRelHistoryRepository.findAll().size();
        // set the field null
        appRelHistory.setUpdateDateTime(null);

        // Create the AppRelHistory, which fails.

        restAppRelHistoryMockMvc.perform(post("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isBadRequest());

        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppRelHistories() throws Exception {
        // Initialize the database
        appRelHistoryRepository.saveAndFlush(appRelHistory);

        // Get all the appRelHistoryList
        restAppRelHistoryMockMvc.perform(get("/api/app-rel-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appRelHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getAppRelHistory() throws Exception {
        // Initialize the database
        appRelHistoryRepository.saveAndFlush(appRelHistory);

        // Get the appRelHistory
        restAppRelHistoryMockMvc.perform(get("/api/app-rel-histories/{id}", appRelHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appRelHistory.getId().intValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAppRelHistory() throws Exception {
        // Get the appRelHistory
        restAppRelHistoryMockMvc.perform(get("/api/app-rel-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppRelHistory() throws Exception {
        // Initialize the database
        appRelHistoryRepository.saveAndFlush(appRelHistory);
        int databaseSizeBeforeUpdate = appRelHistoryRepository.findAll().size();

        // Update the appRelHistory
        AppRelHistory updatedAppRelHistory = appRelHistoryRepository.findOne(appRelHistory.getId());
        updatedAppRelHistory
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restAppRelHistoryMockMvc.perform(put("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppRelHistory)))
            .andExpect(status().isOk());

        // Validate the AppRelHistory in the database
        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeUpdate);
        AppRelHistory testAppRelHistory = appRelHistoryList.get(appRelHistoryList.size() - 1);
        assertThat(testAppRelHistory.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testAppRelHistory.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testAppRelHistory.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testAppRelHistory.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAppRelHistory() throws Exception {
        int databaseSizeBeforeUpdate = appRelHistoryRepository.findAll().size();

        // Create the AppRelHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppRelHistoryMockMvc.perform(put("/api/app-rel-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appRelHistory)))
            .andExpect(status().isCreated());

        // Validate the AppRelHistory in the database
        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppRelHistory() throws Exception {
        // Initialize the database
        appRelHistoryRepository.saveAndFlush(appRelHistory);
        int databaseSizeBeforeDelete = appRelHistoryRepository.findAll().size();

        // Get the appRelHistory
        restAppRelHistoryMockMvc.perform(delete("/api/app-rel-histories/{id}", appRelHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppRelHistory> appRelHistoryList = appRelHistoryRepository.findAll();
        assertThat(appRelHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppRelHistory.class);
        AppRelHistory appRelHistory1 = new AppRelHistory();
        appRelHistory1.setId(1L);
        AppRelHistory appRelHistory2 = new AppRelHistory();
        appRelHistory2.setId(appRelHistory1.getId());
        assertThat(appRelHistory1).isEqualTo(appRelHistory2);
        appRelHistory2.setId(2L);
        assertThat(appRelHistory1).isNotEqualTo(appRelHistory2);
        appRelHistory1.setId(null);
        assertThat(appRelHistory1).isNotEqualTo(appRelHistory2);
    }
}
