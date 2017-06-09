package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.EmailAddress;
import gov.ca.cwds.cals.rest.api.repository.EmailAddressRepository;
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
 * Test class for the EmailAddressResource REST controller.
 *
 * @see EmailAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EmailAddressResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EmailAddressRepository emailAddressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailAddressMockMvc;

    private EmailAddress emailAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailAddressResource emailAddressResource = new EmailAddressResource(emailAddressRepository);
        this.restEmailAddressMockMvc = MockMvcBuilders.standaloneSetup(emailAddressResource)
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
    public static EmailAddress createEntity(EntityManager em) {
        EmailAddress emailAddress = new EmailAddress()
            .email(DEFAULT_EMAIL)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return emailAddress;
    }

    @Before
    public void initTest() {
        emailAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailAddress() throws Exception {
        int databaseSizeBeforeCreate = emailAddressRepository.findAll().size();

        // Create the EmailAddress
        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isCreated());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeCreate + 1);
        EmailAddress testEmailAddress = emailAddressList.get(emailAddressList.size() - 1);
        assertThat(testEmailAddress.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmailAddress.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testEmailAddress.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testEmailAddress.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testEmailAddress.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createEmailAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailAddressRepository.findAll().size();

        // Create the EmailAddress with an existing ID
        emailAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setEmail(null);

        // Create the EmailAddress, which fails.

        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setCreateUserId(null);

        // Create the EmailAddress, which fails.

        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setCreateDateTime(null);

        // Create the EmailAddress, which fails.

        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setUpdateUserId(null);

        // Create the EmailAddress, which fails.

        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setUpdateDateTime(null);

        // Create the EmailAddress, which fails.

        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailAddresses() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);

        // Get all the emailAddressList
        restEmailAddressMockMvc.perform(get("/api/email-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getEmailAddress() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);

        // Get the emailAddress
        restEmailAddressMockMvc.perform(get("/api/email-addresses/{id}", emailAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailAddress.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEmailAddress() throws Exception {
        // Get the emailAddress
        restEmailAddressMockMvc.perform(get("/api/email-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailAddress() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);
        int databaseSizeBeforeUpdate = emailAddressRepository.findAll().size();

        // Update the emailAddress
        EmailAddress updatedEmailAddress = emailAddressRepository.findOne(emailAddress.getId());
        updatedEmailAddress
            .email(UPDATED_EMAIL)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restEmailAddressMockMvc.perform(put("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmailAddress)))
            .andExpect(status().isOk());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeUpdate);
        EmailAddress testEmailAddress = emailAddressList.get(emailAddressList.size() - 1);
        assertThat(testEmailAddress.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailAddress.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testEmailAddress.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testEmailAddress.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testEmailAddress.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailAddress() throws Exception {
        int databaseSizeBeforeUpdate = emailAddressRepository.findAll().size();

        // Create the EmailAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailAddressMockMvc.perform(put("/api/email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isCreated());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmailAddress() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);
        int databaseSizeBeforeDelete = emailAddressRepository.findAll().size();

        // Get the emailAddress
        restEmailAddressMockMvc.perform(delete("/api/email-addresses/{id}", emailAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailAddress.class);
        EmailAddress emailAddress1 = new EmailAddress();
        emailAddress1.setId(1L);
        EmailAddress emailAddress2 = new EmailAddress();
        emailAddress2.setId(emailAddress1.getId());
        assertThat(emailAddress1).isEqualTo(emailAddress2);
        emailAddress2.setId(2L);
        assertThat(emailAddress1).isNotEqualTo(emailAddress2);
        emailAddress1.setId(null);
        assertThat(emailAddress1).isNotEqualTo(emailAddress2);
    }
}
