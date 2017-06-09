package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonAddress;
import gov.ca.cwds.cals.rest.api.repository.PersonAddressRepository;
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
 * Test class for the PersonAddressResource REST controller.
 *
 * @see PersonAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonAddressResourceIntTest {

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonAddressMockMvc;

    private PersonAddress personAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonAddressResource personAddressResource = new PersonAddressResource(personAddressRepository);
        this.restPersonAddressMockMvc = MockMvcBuilders.standaloneSetup(personAddressResource)
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
    public static PersonAddress createEntity(EntityManager em) {
        PersonAddress personAddress = new PersonAddress()
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return personAddress;
    }

    @Before
    public void initTest() {
        personAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonAddress() throws Exception {
        int databaseSizeBeforeCreate = personAddressRepository.findAll().size();

        // Create the PersonAddress
        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isCreated());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PersonAddress testPersonAddress = personAddressList.get(personAddressList.size() - 1);
        assertThat(testPersonAddress.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testPersonAddress.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testPersonAddress.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testPersonAddress.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createPersonAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personAddressRepository.findAll().size();

        // Create the PersonAddress with an existing ID
        personAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personAddressRepository.findAll().size();
        // set the field null
        personAddress.setCreateUserId(null);

        // Create the PersonAddress, which fails.

        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isBadRequest());

        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personAddressRepository.findAll().size();
        // set the field null
        personAddress.setCreateDateTime(null);

        // Create the PersonAddress, which fails.

        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isBadRequest());

        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personAddressRepository.findAll().size();
        // set the field null
        personAddress.setUpdateUserId(null);

        // Create the PersonAddress, which fails.

        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isBadRequest());

        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personAddressRepository.findAll().size();
        // set the field null
        personAddress.setUpdateDateTime(null);

        // Create the PersonAddress, which fails.

        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isBadRequest());

        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonAddresses() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);

        // Get all the personAddressList
        restPersonAddressMockMvc.perform(get("/api/person-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getPersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);

        // Get the personAddress
        restPersonAddressMockMvc.perform(get("/api/person-addresses/{id}", personAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personAddress.getId().intValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonAddress() throws Exception {
        // Get the personAddress
        restPersonAddressMockMvc.perform(get("/api/person-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);
        int databaseSizeBeforeUpdate = personAddressRepository.findAll().size();

        // Update the personAddress
        PersonAddress updatedPersonAddress = personAddressRepository.findOne(personAddress.getId());
        updatedPersonAddress
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restPersonAddressMockMvc.perform(put("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonAddress)))
            .andExpect(status().isOk());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeUpdate);
        PersonAddress testPersonAddress = personAddressList.get(personAddressList.size() - 1);
        assertThat(testPersonAddress.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testPersonAddress.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testPersonAddress.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testPersonAddress.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonAddress() throws Exception {
        int databaseSizeBeforeUpdate = personAddressRepository.findAll().size();

        // Create the PersonAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonAddressMockMvc.perform(put("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddress)))
            .andExpect(status().isCreated());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);
        int databaseSizeBeforeDelete = personAddressRepository.findAll().size();

        // Get the personAddress
        restPersonAddressMockMvc.perform(delete("/api/person-addresses/{id}", personAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonAddress.class);
        PersonAddress personAddress1 = new PersonAddress();
        personAddress1.setId(1L);
        PersonAddress personAddress2 = new PersonAddress();
        personAddress2.setId(personAddress1.getId());
        assertThat(personAddress1).isEqualTo(personAddress2);
        personAddress2.setId(2L);
        assertThat(personAddress1).isNotEqualTo(personAddress2);
        personAddress1.setId(null);
        assertThat(personAddress1).isNotEqualTo(personAddress2);
    }
}
