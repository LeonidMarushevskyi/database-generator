package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.HouseholdChild;
import gov.ca.cwds.cals.rest.api.domain.Household;
import gov.ca.cwds.cals.rest.api.repository.HouseholdChildRepository;
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
 * Test class for the HouseholdChildResource REST controller.
 *
 * @see HouseholdChildResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class HouseholdChildResourceIntTest {

    private static final Boolean DEFAULT_IS_FINANCIALLY_SUPPORTED = false;
    private static final Boolean UPDATED_IS_FINANCIALLY_SUPPORTED = true;

    private static final Boolean DEFAULT_IS_ADOPTED = false;
    private static final Boolean UPDATED_IS_ADOPTED = true;

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HouseholdChildRepository householdChildRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHouseholdChildMockMvc;

    private HouseholdChild householdChild;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HouseholdChildResource householdChildResource = new HouseholdChildResource(householdChildRepository);
        this.restHouseholdChildMockMvc = MockMvcBuilders.standaloneSetup(householdChildResource)
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
    public static HouseholdChild createEntity(EntityManager em) {
        HouseholdChild householdChild = new HouseholdChild()
            .isFinanciallySupported(DEFAULT_IS_FINANCIALLY_SUPPORTED)
            .isAdopted(DEFAULT_IS_ADOPTED)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        // Add required entity
        Household household = HouseholdResourceIntTest.createEntity(em);
        em.persist(household);
        em.flush();
        householdChild.setHousehold(household);
        return householdChild;
    }

    @Before
    public void initTest() {
        householdChild = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseholdChild() throws Exception {
        int databaseSizeBeforeCreate = householdChildRepository.findAll().size();

        // Create the HouseholdChild
        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isCreated());

        // Validate the HouseholdChild in the database
        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeCreate + 1);
        HouseholdChild testHouseholdChild = householdChildList.get(householdChildList.size() - 1);
        assertThat(testHouseholdChild.isIsFinanciallySupported()).isEqualTo(DEFAULT_IS_FINANCIALLY_SUPPORTED);
        assertThat(testHouseholdChild.isIsAdopted()).isEqualTo(DEFAULT_IS_ADOPTED);
        assertThat(testHouseholdChild.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testHouseholdChild.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testHouseholdChild.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testHouseholdChild.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createHouseholdChildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = householdChildRepository.findAll().size();

        // Create the HouseholdChild with an existing ID
        householdChild.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIsFinanciallySupportedIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setIsFinanciallySupported(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAdoptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setIsAdopted(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setCreateUserId(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setCreateDateTime(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setUpdateUserId(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = householdChildRepository.findAll().size();
        // set the field null
        householdChild.setUpdateDateTime(null);

        // Create the HouseholdChild, which fails.

        restHouseholdChildMockMvc.perform(post("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isBadRequest());

        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHouseholdChildren() throws Exception {
        // Initialize the database
        householdChildRepository.saveAndFlush(householdChild);

        // Get all the householdChildList
        restHouseholdChildMockMvc.perform(get("/api/household-children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(householdChild.getId().intValue())))
            .andExpect(jsonPath("$.[*].isFinanciallySupported").value(hasItem(DEFAULT_IS_FINANCIALLY_SUPPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isAdopted").value(hasItem(DEFAULT_IS_ADOPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getHouseholdChild() throws Exception {
        // Initialize the database
        householdChildRepository.saveAndFlush(householdChild);

        // Get the householdChild
        restHouseholdChildMockMvc.perform(get("/api/household-children/{id}", householdChild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(householdChild.getId().intValue()))
            .andExpect(jsonPath("$.isFinanciallySupported").value(DEFAULT_IS_FINANCIALLY_SUPPORTED.booleanValue()))
            .andExpect(jsonPath("$.isAdopted").value(DEFAULT_IS_ADOPTED.booleanValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingHouseholdChild() throws Exception {
        // Get the householdChild
        restHouseholdChildMockMvc.perform(get("/api/household-children/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseholdChild() throws Exception {
        // Initialize the database
        householdChildRepository.saveAndFlush(householdChild);
        int databaseSizeBeforeUpdate = householdChildRepository.findAll().size();

        // Update the householdChild
        HouseholdChild updatedHouseholdChild = householdChildRepository.findOne(householdChild.getId());
        updatedHouseholdChild
            .isFinanciallySupported(UPDATED_IS_FINANCIALLY_SUPPORTED)
            .isAdopted(UPDATED_IS_ADOPTED)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restHouseholdChildMockMvc.perform(put("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHouseholdChild)))
            .andExpect(status().isOk());

        // Validate the HouseholdChild in the database
        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeUpdate);
        HouseholdChild testHouseholdChild = householdChildList.get(householdChildList.size() - 1);
        assertThat(testHouseholdChild.isIsFinanciallySupported()).isEqualTo(UPDATED_IS_FINANCIALLY_SUPPORTED);
        assertThat(testHouseholdChild.isIsAdopted()).isEqualTo(UPDATED_IS_ADOPTED);
        assertThat(testHouseholdChild.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testHouseholdChild.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testHouseholdChild.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testHouseholdChild.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseholdChild() throws Exception {
        int databaseSizeBeforeUpdate = householdChildRepository.findAll().size();

        // Create the HouseholdChild

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHouseholdChildMockMvc.perform(put("/api/household-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(householdChild)))
            .andExpect(status().isCreated());

        // Validate the HouseholdChild in the database
        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHouseholdChild() throws Exception {
        // Initialize the database
        householdChildRepository.saveAndFlush(householdChild);
        int databaseSizeBeforeDelete = householdChildRepository.findAll().size();

        // Get the householdChild
        restHouseholdChildMockMvc.perform(delete("/api/household-children/{id}", householdChild.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HouseholdChild> householdChildList = householdChildRepository.findAll();
        assertThat(householdChildList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseholdChild.class);
        HouseholdChild householdChild1 = new HouseholdChild();
        householdChild1.setId(1L);
        HouseholdChild householdChild2 = new HouseholdChild();
        householdChild2.setId(householdChild1.getId());
        assertThat(householdChild1).isEqualTo(householdChild2);
        householdChild2.setId(2L);
        assertThat(householdChild1).isNotEqualTo(householdChild2);
        householdChild1.setId(null);
        assertThat(householdChild1).isNotEqualTo(householdChild2);
    }
}
