package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.ChildPreferences;
import gov.ca.cwds.cals.rest.api.repository.ChildPreferencesRepository;
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
 * Test class for the ChildPreferencesResource REST controller.
 *
 * @see ChildPreferencesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class ChildPreferencesResourceIntTest {

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ChildPreferencesRepository childPreferencesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChildPreferencesMockMvc;

    private ChildPreferences childPreferences;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChildPreferencesResource childPreferencesResource = new ChildPreferencesResource(childPreferencesRepository);
        this.restChildPreferencesMockMvc = MockMvcBuilders.standaloneSetup(childPreferencesResource)
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
    public static ChildPreferences createEntity(EntityManager em) {
        ChildPreferences childPreferences = new ChildPreferences()
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return childPreferences;
    }

    @Before
    public void initTest() {
        childPreferences = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildPreferences() throws Exception {
        int databaseSizeBeforeCreate = childPreferencesRepository.findAll().size();

        // Create the ChildPreferences
        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isCreated());

        // Validate the ChildPreferences in the database
        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeCreate + 1);
        ChildPreferences testChildPreferences = childPreferencesList.get(childPreferencesList.size() - 1);
        assertThat(testChildPreferences.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testChildPreferences.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testChildPreferences.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testChildPreferences.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createChildPreferencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childPreferencesRepository.findAll().size();

        // Create the ChildPreferences with an existing ID
        childPreferences.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = childPreferencesRepository.findAll().size();
        // set the field null
        childPreferences.setCreateUserId(null);

        // Create the ChildPreferences, which fails.

        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isBadRequest());

        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = childPreferencesRepository.findAll().size();
        // set the field null
        childPreferences.setCreateDateTime(null);

        // Create the ChildPreferences, which fails.

        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isBadRequest());

        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = childPreferencesRepository.findAll().size();
        // set the field null
        childPreferences.setUpdateUserId(null);

        // Create the ChildPreferences, which fails.

        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isBadRequest());

        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = childPreferencesRepository.findAll().size();
        // set the field null
        childPreferences.setUpdateDateTime(null);

        // Create the ChildPreferences, which fails.

        restChildPreferencesMockMvc.perform(post("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isBadRequest());

        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChildPreferences() throws Exception {
        // Initialize the database
        childPreferencesRepository.saveAndFlush(childPreferences);

        // Get all the childPreferencesList
        restChildPreferencesMockMvc.perform(get("/api/child-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childPreferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getChildPreferences() throws Exception {
        // Initialize the database
        childPreferencesRepository.saveAndFlush(childPreferences);

        // Get the childPreferences
        restChildPreferencesMockMvc.perform(get("/api/child-preferences/{id}", childPreferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(childPreferences.getId().intValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingChildPreferences() throws Exception {
        // Get the childPreferences
        restChildPreferencesMockMvc.perform(get("/api/child-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildPreferences() throws Exception {
        // Initialize the database
        childPreferencesRepository.saveAndFlush(childPreferences);
        int databaseSizeBeforeUpdate = childPreferencesRepository.findAll().size();

        // Update the childPreferences
        ChildPreferences updatedChildPreferences = childPreferencesRepository.findOne(childPreferences.getId());
        updatedChildPreferences
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restChildPreferencesMockMvc.perform(put("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChildPreferences)))
            .andExpect(status().isOk());

        // Validate the ChildPreferences in the database
        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeUpdate);
        ChildPreferences testChildPreferences = childPreferencesList.get(childPreferencesList.size() - 1);
        assertThat(testChildPreferences.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testChildPreferences.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testChildPreferences.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testChildPreferences.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingChildPreferences() throws Exception {
        int databaseSizeBeforeUpdate = childPreferencesRepository.findAll().size();

        // Create the ChildPreferences

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChildPreferencesMockMvc.perform(put("/api/child-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(childPreferences)))
            .andExpect(status().isCreated());

        // Validate the ChildPreferences in the database
        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChildPreferences() throws Exception {
        // Initialize the database
        childPreferencesRepository.saveAndFlush(childPreferences);
        int databaseSizeBeforeDelete = childPreferencesRepository.findAll().size();

        // Get the childPreferences
        restChildPreferencesMockMvc.perform(delete("/api/child-preferences/{id}", childPreferences.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChildPreferences> childPreferencesList = childPreferencesRepository.findAll();
        assertThat(childPreferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildPreferences.class);
        ChildPreferences childPreferences1 = new ChildPreferences();
        childPreferences1.setId(1L);
        ChildPreferences childPreferences2 = new ChildPreferences();
        childPreferences2.setId(childPreferences1.getId());
        assertThat(childPreferences1).isEqualTo(childPreferences2);
        childPreferences2.setId(2L);
        assertThat(childPreferences1).isNotEqualTo(childPreferences2);
        childPreferences1.setId(null);
        assertThat(childPreferences1).isNotEqualTo(childPreferences2);
    }
}
