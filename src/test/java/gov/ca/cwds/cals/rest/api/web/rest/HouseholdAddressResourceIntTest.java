package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.HouseholdAddress;
import gov.ca.cwds.cals.rest.api.repository.HouseholdAddressRepository;
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
 * Test class for the HouseholdAddressResource REST controller.
 *
 * @see HouseholdAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class HouseholdAddressResourceIntTest {

    private static final Boolean DEFAULT_IS_WEAPONS_IN_HOME = false;
    private static final Boolean UPDATED_IS_WEAPONS_IN_HOME = true;

    private static final String DEFAULT_DIRECTIONS_TO_HOME = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS_TO_HOME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HouseholdAddressRepository householdAddressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHouseholdAddressMockMvc;

    private HouseholdAddress householdAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HouseholdAddressResource householdAddressResource = new HouseholdAddressResource(householdAddressRepository);
        this.restHouseholdAddressMockMvc = MockMvcBuilders.standaloneSetup(householdAddressResource)
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
    public static HouseholdAddress createEntity(EntityManager em) {
        HouseholdAddress householdAddress = new HouseholdAddress()
            .isWeaponsInHome(DEFAULT_IS_WEAPONS_IN_HOME)
            .directionsToHome(DEFAULT_DIRECTIONS_TO_HOME)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return householdAddress;
    }

    @Before
    public void initTest() {
        householdAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseholdAddress() throws Exception {
        int databaseSizeBeforeCreate = householdAddressRepository.findAll().size();

        // Create the HouseholdAddress
        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isCreated());

        // Validate the HouseholdAddress in the database
        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeCreate + 1);
        HouseholdAddress testHouseholdAddress = householdAddressList.get(householdAddressList.size() - 1);
        assertThat(testHouseholdAddress.isIsWeaponsInHome()).isEqualTo(DEFAULT_IS_WEAPONS_IN_HOME);
        assertThat(testHouseholdAddress.getDirectionsToHome()).isEqualTo(DEFAULT_DIRECTIONS_TO_HOME);
        assertThat(testHouseholdAddress.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testHouseholdAddress.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testHouseholdAddress.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testHouseholdAddress.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createHouseholdAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = householdAddressRepository.findAll().size();

        // Create the HouseholdAddress with an existing ID
        householdAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIsWeaponsInHomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAddressRepository.findAll().size();
        // set the field null
        householdAddress.setIsWeaponsInHome(null);

        // Create the HouseholdAddress, which fails.

        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAddressRepository.findAll().size();
        // set the field null
        householdAddress.setCreateUserId(null);

        // Create the HouseholdAddress, which fails.

        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAddressRepository.findAll().size();
        // set the field null
        householdAddress.setCreateDateTime(null);

        // Create the HouseholdAddress, which fails.

        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAddressRepository.findAll().size();
        // set the field null
        householdAddress.setUpdateUserId(null);

        // Create the HouseholdAddress, which fails.

        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdAddressRepository.findAll().size();
        // set the field null
        householdAddress.setUpdateDateTime(null);

        // Create the HouseholdAddress, which fails.

        restHouseholdAddressMockMvc.perform(post("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isBadRequest());

        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHouseholdAddresses() throws Exception {
        // Initialize the database
        householdAddressRepository.saveAndFlush(householdAddress);

        // Get all the householdAddressList
        restHouseholdAddressMockMvc.perform(get("/api/household-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(householdAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].isWeaponsInHome").value(hasItem(DEFAULT_IS_WEAPONS_IN_HOME.booleanValue())))
            .andExpect(jsonPath("$.[*].directionsToHome").value(hasItem(DEFAULT_DIRECTIONS_TO_HOME.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getHouseholdAddress() throws Exception {
        // Initialize the database
        householdAddressRepository.saveAndFlush(householdAddress);

        // Get the householdAddress
        restHouseholdAddressMockMvc.perform(get("/api/household-addresses/{id}", householdAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(householdAddress.getId().intValue()))
            .andExpect(jsonPath("$.isWeaponsInHome").value(DEFAULT_IS_WEAPONS_IN_HOME.booleanValue()))
            .andExpect(jsonPath("$.directionsToHome").value(DEFAULT_DIRECTIONS_TO_HOME.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingHouseholdAddress() throws Exception {
        // Get the householdAddress
        restHouseholdAddressMockMvc.perform(get("/api/household-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseholdAddress() throws Exception {
        // Initialize the database
        householdAddressRepository.saveAndFlush(householdAddress);
        int databaseSizeBeforeUpdate = householdAddressRepository.findAll().size();

        // Update the householdAddress
        HouseholdAddress updatedHouseholdAddress = householdAddressRepository.findOne(householdAddress.getId());
        updatedHouseholdAddress
            .isWeaponsInHome(UPDATED_IS_WEAPONS_IN_HOME)
            .directionsToHome(UPDATED_DIRECTIONS_TO_HOME)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restHouseholdAddressMockMvc.perform(put("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHouseholdAddress)))
            .andExpect(status().isOk());

        // Validate the HouseholdAddress in the database
        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeUpdate);
        HouseholdAddress testHouseholdAddress = householdAddressList.get(householdAddressList.size() - 1);
        assertThat(testHouseholdAddress.isIsWeaponsInHome()).isEqualTo(UPDATED_IS_WEAPONS_IN_HOME);
        assertThat(testHouseholdAddress.getDirectionsToHome()).isEqualTo(UPDATED_DIRECTIONS_TO_HOME);
        assertThat(testHouseholdAddress.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testHouseholdAddress.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testHouseholdAddress.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testHouseholdAddress.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseholdAddress() throws Exception {
        int databaseSizeBeforeUpdate = householdAddressRepository.findAll().size();

        // Create the HouseholdAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHouseholdAddressMockMvc.perform(put("/api/household-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdAddress)))
            .andExpect(status().isCreated());

        // Validate the HouseholdAddress in the database
        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHouseholdAddress() throws Exception {
        // Initialize the database
        householdAddressRepository.saveAndFlush(householdAddress);
        int databaseSizeBeforeDelete = householdAddressRepository.findAll().size();

        // Get the householdAddress
        restHouseholdAddressMockMvc.perform(delete("/api/household-addresses/{id}", householdAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HouseholdAddress> householdAddressList = householdAddressRepository.findAll();
        assertThat(householdAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseholdAddress.class);
        HouseholdAddress householdAddress1 = new HouseholdAddress();
        householdAddress1.setId(1L);
        HouseholdAddress householdAddress2 = new HouseholdAddress();
        householdAddress2.setId(householdAddress1.getId());
        assertThat(householdAddress1).isEqualTo(householdAddress2);
        householdAddress2.setId(2L);
        assertThat(householdAddress1).isNotEqualTo(householdAddress2);
        householdAddress1.setId(null);
        assertThat(householdAddress1).isNotEqualTo(householdAddress2);
    }
}
