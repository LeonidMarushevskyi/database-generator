package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonPreviousName;
import gov.ca.cwds.cals.rest.api.repository.PersonPreviousNameRepository;
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
 * Test class for the PersonPreviousNameResource REST controller.
 *
 * @see PersonPreviousNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonPreviousNameResourceIntTest {

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
    private PersonPreviousNameRepository personPreviousNameRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonPreviousNameMockMvc;

    private PersonPreviousName personPreviousName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonPreviousNameResource personPreviousNameResource = new PersonPreviousNameResource(personPreviousNameRepository);
        this.restPersonPreviousNameMockMvc = MockMvcBuilders.standaloneSetup(personPreviousNameResource)
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
    public static PersonPreviousName createEntity(EntityManager em) {
        PersonPreviousName personPreviousName = new PersonPreviousName()
            .name(DEFAULT_NAME)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return personPreviousName;
    }

    @Before
    public void initTest() {
        personPreviousName = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPreviousName() throws Exception {
        int databaseSizeBeforeCreate = personPreviousNameRepository.findAll().size();

        // Create the PersonPreviousName
        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isCreated());

        // Validate the PersonPreviousName in the database
        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPreviousName testPersonPreviousName = personPreviousNameList.get(personPreviousNameList.size() - 1);
        assertThat(testPersonPreviousName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonPreviousName.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testPersonPreviousName.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testPersonPreviousName.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testPersonPreviousName.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createPersonPreviousNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPreviousNameRepository.findAll().size();

        // Create the PersonPreviousName with an existing ID
        personPreviousName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPreviousNameRepository.findAll().size();
        // set the field null
        personPreviousName.setName(null);

        // Create the PersonPreviousName, which fails.

        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPreviousNameRepository.findAll().size();
        // set the field null
        personPreviousName.setCreateUserId(null);

        // Create the PersonPreviousName, which fails.

        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPreviousNameRepository.findAll().size();
        // set the field null
        personPreviousName.setCreateDateTime(null);

        // Create the PersonPreviousName, which fails.

        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPreviousNameRepository.findAll().size();
        // set the field null
        personPreviousName.setUpdateUserId(null);

        // Create the PersonPreviousName, which fails.

        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPreviousNameRepository.findAll().size();
        // set the field null
        personPreviousName.setUpdateDateTime(null);

        // Create the PersonPreviousName, which fails.

        restPersonPreviousNameMockMvc.perform(post("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isBadRequest());

        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonPreviousNames() throws Exception {
        // Initialize the database
        personPreviousNameRepository.saveAndFlush(personPreviousName);

        // Get all the personPreviousNameList
        restPersonPreviousNameMockMvc.perform(get("/api/person-previous-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPreviousName.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getPersonPreviousName() throws Exception {
        // Initialize the database
        personPreviousNameRepository.saveAndFlush(personPreviousName);

        // Get the personPreviousName
        restPersonPreviousNameMockMvc.perform(get("/api/person-previous-names/{id}", personPreviousName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPreviousName.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPreviousName() throws Exception {
        // Get the personPreviousName
        restPersonPreviousNameMockMvc.perform(get("/api/person-previous-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPreviousName() throws Exception {
        // Initialize the database
        personPreviousNameRepository.saveAndFlush(personPreviousName);
        int databaseSizeBeforeUpdate = personPreviousNameRepository.findAll().size();

        // Update the personPreviousName
        PersonPreviousName updatedPersonPreviousName = personPreviousNameRepository.findOne(personPreviousName.getId());
        updatedPersonPreviousName
            .name(UPDATED_NAME)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restPersonPreviousNameMockMvc.perform(put("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonPreviousName)))
            .andExpect(status().isOk());

        // Validate the PersonPreviousName in the database
        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeUpdate);
        PersonPreviousName testPersonPreviousName = personPreviousNameList.get(personPreviousNameList.size() - 1);
        assertThat(testPersonPreviousName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonPreviousName.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testPersonPreviousName.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testPersonPreviousName.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testPersonPreviousName.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPreviousName() throws Exception {
        int databaseSizeBeforeUpdate = personPreviousNameRepository.findAll().size();

        // Create the PersonPreviousName

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonPreviousNameMockMvc.perform(put("/api/person-previous-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPreviousName)))
            .andExpect(status().isCreated());

        // Validate the PersonPreviousName in the database
        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonPreviousName() throws Exception {
        // Initialize the database
        personPreviousNameRepository.saveAndFlush(personPreviousName);
        int databaseSizeBeforeDelete = personPreviousNameRepository.findAll().size();

        // Get the personPreviousName
        restPersonPreviousNameMockMvc.perform(delete("/api/person-previous-names/{id}", personPreviousName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPreviousName> personPreviousNameList = personPreviousNameRepository.findAll();
        assertThat(personPreviousNameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPreviousName.class);
        PersonPreviousName personPreviousName1 = new PersonPreviousName();
        personPreviousName1.setId(1L);
        PersonPreviousName personPreviousName2 = new PersonPreviousName();
        personPreviousName2.setId(personPreviousName1.getId());
        assertThat(personPreviousName1).isEqualTo(personPreviousName2);
        personPreviousName2.setId(2L);
        assertThat(personPreviousName1).isNotEqualTo(personPreviousName2);
        personPreviousName1.setId(null);
        assertThat(personPreviousName1).isNotEqualTo(personPreviousName2);
    }
}
