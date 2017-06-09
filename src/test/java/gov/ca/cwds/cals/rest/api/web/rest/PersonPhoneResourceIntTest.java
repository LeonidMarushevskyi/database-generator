package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonPhone;
import gov.ca.cwds.cals.rest.api.repository.PersonPhoneRepository;
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
 * Test class for the PersonPhoneResource REST controller.
 *
 * @see PersonPhoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonPhoneResourceIntTest {

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PersonPhoneRepository personPhoneRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonPhoneMockMvc;

    private PersonPhone personPhone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonPhoneResource personPhoneResource = new PersonPhoneResource(personPhoneRepository);
        this.restPersonPhoneMockMvc = MockMvcBuilders.standaloneSetup(personPhoneResource)
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
    public static PersonPhone createEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return personPhone;
    }

    @Before
    public void initTest() {
        personPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPhone() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isCreated());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testPersonPhone.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testPersonPhone.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testPersonPhone.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createPersonPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone with an existing ID
        personPhone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setCreateUserId(null);

        // Create the PersonPhone, which fails.

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setCreateDateTime(null);

        // Create the PersonPhone, which fails.

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setUpdateUserId(null);

        // Create the PersonPhone, which fails.

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setUpdateDateTime(null);

        // Create the PersonPhone, which fails.

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonPhones() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getPersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", personPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPhone.getId().intValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPhone() throws Exception {
        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Update the personPhone
        PersonPhone updatedPersonPhone = personPhoneRepository.findOne(personPhone.getId());
        updatedPersonPhone
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonPhone)))
            .andExpect(status().isOk());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testPersonPhone.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testPersonPhone.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testPersonPhone.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPhone() throws Exception {
        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Create the PersonPhone

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhone)))
            .andExpect(status().isCreated());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        int databaseSizeBeforeDelete = personPhoneRepository.findAll().size();

        // Get the personPhone
        restPersonPhoneMockMvc.perform(delete("/api/person-phones/{id}", personPhone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPhone.class);
        PersonPhone personPhone1 = new PersonPhone();
        personPhone1.setId(1L);
        PersonPhone personPhone2 = new PersonPhone();
        personPhone2.setId(personPhone1.getId());
        assertThat(personPhone1).isEqualTo(personPhone2);
        personPhone2.setId(2L);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
        personPhone1.setId(null);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
    }
}
