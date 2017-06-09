package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PhoneNumber;
import gov.ca.cwds.cals.rest.api.repository.PhoneNumberRepository;
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
 * Test class for the PhoneNumberResource REST controller.
 *
 * @see PhoneNumberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PhoneNumberResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPhoneNumberMockMvc;

    private PhoneNumber phoneNumber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhoneNumberResource phoneNumberResource = new PhoneNumberResource(phoneNumberRepository);
        this.restPhoneNumberMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberResource)
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
    public static PhoneNumber createEntity(EntityManager em) {
        PhoneNumber phoneNumber = new PhoneNumber()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return phoneNumber;
    }

    @Before
    public void initTest() {
        phoneNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPhoneNumber.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testPhoneNumber.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testPhoneNumber.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testPhoneNumber.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createPhoneNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber with an existing ID
        phoneNumber.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setPhoneNumber(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setCreateUserId(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setCreateDateTime(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setUpdateUserId(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setUpdateDateTime(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get all the phoneNumberList
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", phoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);
        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Update the phoneNumber
        PhoneNumber updatedPhoneNumber = phoneNumberRepository.findOne(phoneNumber.getId());
        updatedPhoneNumber
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhoneNumber)))
            .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPhoneNumber.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testPhoneNumber.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testPhoneNumber.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testPhoneNumber.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumber() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);
        int databaseSizeBeforeDelete = phoneNumberRepository.findAll().size();

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(delete("/api/phone-numbers/{id}", phoneNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumber.class);
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(1L);
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(phoneNumber1.getId());
        assertThat(phoneNumber1).isEqualTo(phoneNumber2);
        phoneNumber2.setId(2L);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
        phoneNumber1.setId(null);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
    }
}
